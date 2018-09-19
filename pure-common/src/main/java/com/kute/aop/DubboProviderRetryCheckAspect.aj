package com.kute.aop;

import com.alibaba.dubbo.rpc.RpcContext;
import com.kute.annotation.DubboProviderRetryCheck;
import com.kute.cache.redis.ShardedRedisUtil;
import com.kute.exception.DubboRetryException;
import com.kute.util.KeyUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DubboProviderRetryCheckAspect {

    private static final Logger logger = LoggerFactory.getLogger(DubboProviderRetryCheckAspect.class);

    @Pointcut("@annotation(com.kute.annotation.DubboProviderRetryCheck)")
    public void pointcut() {

    }

    @Before(value = "pointcut() && @annotation(check)")
    public void providerRetryCheck(JoinPoint joinPoint, DubboProviderRetryCheck check) {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        // dubbo service class (interface)
        Class<?> serviceClass = method.getDeclaringClass();
        long expiredMillis = check.expiredMillis();
        String methodName = method.getName();

        String methodKey = KeyUtil.getMethodKey(serviceClass, methodName);

        logger.info("Dubbo providerRetryCheck parameters, method={}, expiredMillis={}", methodKey, expiredMillis);
        try {
            // uuid
            String uuid = RpcContext.getContext().getAttachment(methodKey);

            if (null != uuid) {

                String redisKey = KeyUtil.getRedisKey(uuid);

                if (ShardedRedisUtil.getInstance().exists(redisKey)) {
                    logger.info("Dubbo providerRetryCheck method[{}] repeat call then return", methodKey);
                    // 若 key 存在，那么认为是 重试（重复调用）
                    throw new DubboRetryException("method[" + methodKey + "] repeat execute");
                }
                // 否则，设置key值并设定过期时间
                ShardedRedisUtil.getInstance().set(redisKey, uuid);
                ShardedRedisUtil.getInstance().expire(redisKey, Long.valueOf(expiredMillis).intValue());
            }

        } catch (Exception e) {
            logger.error("Dubbo providerRetryCheck exception in [{}]", methodKey, e);
            if ("DubboRetryException".equalsIgnoreCase(e.getClass().getSimpleName())) {
                throw e;
            }
        }

    }

}
