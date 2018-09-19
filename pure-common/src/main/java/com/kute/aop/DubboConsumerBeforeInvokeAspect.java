package com.kute.aop;

import com.alibaba.dubbo.rpc.RpcContext;
import com.kute.annotation.DubboConsumerBeforeInvoke;
import com.kute.util.KeyUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * created by kute on 2018/08/06 13:01
 * <p>
 * dubbo 方法调用前 添加 uuid 作为 唯一id区分重试
 */
@Aspect
@Component
public class DubboConsumerBeforeInvokeAspect {

    private static final Logger logger = LoggerFactory.getLogger(DubboConsumerBeforeInvokeAspect.class);

    @Pointcut("@annotation(com.kute.annotation.DubboConsumerBeforeInvoke)")
    public void pointcut() {

    }

    @Before(value = "pointcut() && @annotation(invoke)")
    public void consumerBeforeInvoke(JoinPoint joinPoint, DubboConsumerBeforeInvoke invoke) {

        if (!invoke.enabled()) {
            logger.warn("Dubbo consumerBeforeInvoke annotation[com.lianjia.sinan.qc.annotation.DubboConsumerBeforeInvoke] is not enabled");
            return;
        }

        Class[] serviceClassArray = invoke.serviceClazz();
        String[] methodArray = invoke.method();

        if (serviceClassArray.length == 0 || serviceClassArray.length != methodArray.length) {
            throw new IllegalArgumentException("Dubbo annotation[com.lianjia.sinan.qc.annotation.DubboConsumerBeforeInvoke] need parameter declare");
        }

        int size = serviceClassArray.length;

        // 对 要调用的每个 dubbo 方法 生成 唯一ID
        for (int i = 0; i < size; i++) {
            Class<?> serviceClass = serviceClassArray[i];
            String dubboMethod = methodArray[i];
            String methodKey = KeyUtil.getMethodKey(serviceClass, dubboMethod);

            String uuid = UUID.randomUUID().toString();
            logger.info("Dubbo consumerBeforeInvoke method[{}] setAttachment in context:{}", methodKey, uuid);
            RpcContext.getContext().setAttachment(methodKey, uuid);

        }
    }

}
