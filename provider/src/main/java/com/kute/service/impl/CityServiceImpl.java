package com.kute.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.google.common.base.Joiner;
import com.kute.annotation.DubboProviderRetryCheck;
import com.kute.cache.redis.ShardedRedisUtil;
import com.kute.exception.BuildCityException;
import com.kute.service.ICityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by kute on 2018/06/26 21:54
 */
@Component("cityService")
// 通过 注解 暴露服务
@Service(
        interfaceClass = ICityService.class,
        parameters = {
//                "findCity.retries", "2"
        }
)
public class CityServiceImpl implements ICityService {

    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    private static final AtomicInteger call = new AtomicInteger(0);

    @Override
    public String findCity(String code, long timeOutMillis) {
        String methodKey = getMethodKey(ICityService.class, "findCity");

        String value = RpcContext.getContext().getAttachment(methodKey);

        logger.info("Dubbo method[{}] parameters:{}, thread={}, urlparameter={}", new Object[]{
                methodKey, value, Thread.currentThread().getId(), RpcContext.getContext().getUrl().getParameter(methodKey)
        });

        if (!"OK".equalsIgnoreCase(ShardedRedisUtil.getInstance().set(methodKey, value, "NX", "PX", timeOutMillis))) {
            logger.info("Dubbo method[{}] repeat call then return, thread={}", methodKey, Thread.currentThread().getId());
            // 若 设置不成功，表示key已存在，即重复调用，所以直接返回
            return "findCity_repeat_" + code;
        }

        try {
            logger.info("findCity-call-normal-logic, thread={}", new Object[]{
                    Thread.currentThread().getId()
            });
            Thread.sleep(timeOutMillis);
        } catch (Exception e) {
            // ignore
        } finally {
            ShardedRedisUtil.getInstance().del(methodKey);
        }
        logger.info("findCity-call-times-over:thread={}", Thread.currentThread().getId());
        return "findCity_" + code;
    }

    @Override
    public String buildCity(String code) {
        if ("1".equalsIgnoreCase(code)) {
            throw new BuildCityException("CityServiceImpl.buildCity.BuildCityException");
        } else if ("2".equalsIgnoreCase(code)) {
            throw new RuntimeException("CityServiceImpl.buildCity.RuntimeException");
        }
        return "buildCity_" + code;
    }

    @DubboProviderRetryCheck()
    @Override
    public String liveCity(String code, long timeOutMillis) {
        int times = call.incrementAndGet();


        String methodKey = getMethodKey(ICityService.class, "liveCity");

        String value = RpcContext.getContext().getAttachment(methodKey);

        logger.info("Dubbo method[{}] parameters:{}, thread={}, urlparameter={}", new Object[]{
                methodKey, value, Thread.currentThread().getId(), RpcContext.getContext().getUrl().getParameter(methodKey)
        });

        if (!"OK".equalsIgnoreCase(ShardedRedisUtil.getInstance().set(methodKey, value, "NX", "PX", timeOutMillis))) {
            logger.info("Dubbo method[{}] repeat call then return, thread={}", methodKey, Thread.currentThread().getId());
            // 若 设置不成功，表示key已存在，即重复调用，所以直接返回
            return "liveCity_repeat_" + code;
        }

        try {
            logger.info("liveCity-call-normal-logic, thread={}", new Object[]{
                    Thread.currentThread().getId()
            });
            Thread.sleep(timeOutMillis);
        } catch (Exception e) {
            // ignore
        } finally {
            ShardedRedisUtil.getInstance().del(methodKey);
        }
        logger.info("liveCity-call-times-over:{}, thread={}", times, Thread.currentThread().getId());
        return "liveCity_" + code;
    }

    private String getMethodKey(Class<?> serviceClass, String methodName) {
        return Joiner.on(".").useForNull("").join(serviceClass.getName(), methodName, "dubbo.retry");
    }
}
