package com.kute.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.cluster.loadbalance.ConsistentHashLoadBalance;
import com.kute.exception.BuildCityException;
import com.kute.service.ICityService;
import org.springframework.stereotype.Component;

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
        if("1".equalsIgnoreCase(code)) {
            throw new BuildCityException("CityServiceImpl.buildCity.BuildCityException");
        } else if ("2".equalsIgnoreCase(code)) {
            throw new RuntimeException("CityServiceImpl.buildCity.RuntimeException");
        }
        return "buildCity_" + code;
    }
}
