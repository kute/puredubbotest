package com.kute.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.cluster.loadbalance.ConsistentHashLoadBalance;
import com.google.common.base.Strings;
import com.kute.annotation.DubboRetryCheck;
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
        if (timeOutMillis > 0) {
            try {
                Thread.sleep(timeOutMillis);
            } catch (InterruptedException e) {
                // ignore
            }
        }
        return code;
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

    @DubboRetryCheck
    @Override
    public String liveCity(String code, long timeOutMillis) {
        int times = call.incrementAndGet();

        try {
            logger.info("liveCity-call-normal-logic, thread={}, value={}", new Object[]{
                    Thread.currentThread().getId(), RpcContext.getContext().getAttachment("livecity_start_key")
            });
            Thread.sleep(timeOutMillis);
        } catch (Exception e) {
            // ignore
        }
        logger.info("liveCity-call-times-over:{}, thread={}", times, Thread.currentThread().getId());
        return "liveCity_" + code;
    }
}
