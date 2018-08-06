package com.kute.aop;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.kute.annotation.DubboRetryCheck;
import com.kute.cache.redis.ShardedRedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DubboRetryCheckAspect {

    private static final String DEFAULT = "default";

    @Around(value = "@annotation(com.kute.annotation.DubboRetryCheck)")
    public Object controllerPointcut(ProceedingJoinPoint proceedingJoinPoint) {

        MethodInvocationProceedingJoinPoint methodInvocationProceedingJoinPoint =
                (MethodInvocationProceedingJoinPoint) proceedingJoinPoint;
        Method method = ((MethodSignature) methodInvocationProceedingJoinPoint.getSignature()).getMethod();

        String methodName = method.getName();

        DubboRetryCheck checkAspect = method.getAnnotation(DubboRetryCheck.class);

        int timeOutMillis = checkAspect.timeOutMillis();

        String retryKey = getMethodCallKey(methodName, null);
        String continueValue = ShardedRedisUtil.getInstance().get(retryKey);
        if (!Strings.isNullOrEmpty(continueValue) && DEFAULT.equalsIgnoreCase(continueValue)) {
            return null;
        } else {
            ShardedRedisUtil.getInstance().set(retryKey, DEFAULT);
            ShardedRedisUtil.getInstance().expire(retryKey, timeOutMillis);
        }

        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            result = "pointcut error return";
        } finally {
            ShardedRedisUtil.getInstance().del(retryKey);
        }
        return result;
    }

    private String getMethodCallKey(String methodName, String[] args) {
        return Joiner.on("_").useForNull("").join(methodName, "call_key");
    }

}
