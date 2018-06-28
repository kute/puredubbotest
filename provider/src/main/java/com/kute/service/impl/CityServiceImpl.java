package com.kute.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.cluster.loadbalance.ConsistentHashLoadBalance;
import com.kute.service.ICityService;
import org.springframework.stereotype.Component;

/**
 * created by kute on 2018/06/26 21:54
 */
@Component("cityService")
// 通过 注解 暴露服务
@Service(interfaceClass = ICityService.class)
public class CityServiceImpl implements ICityService {
    @Override
    public String findCity(String code) {

        return code;
    }
}
