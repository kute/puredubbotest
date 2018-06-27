package com.kute.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kute.domain.User;
import com.kute.service.ICityService;
import com.kute.service.IConsumerService;
import com.kute.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * created by kute on 2018/05/14 01:36
 */
@Service("consumerService")
public class ConsumerServiceImpl implements IConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Resource
    private IUserService userService;

    /**
     * 通过注解 引入 服务
     */
    @Reference(interfaceClass = ICityService.class)
    private ICityService cityService;

    @Override
    public User getUser(Integer userId) {

        logger.info("get user by rpc call:{}", userId);
        return userService.getUser(userId);
    }

    @Override
    public String findCity(String code) {
        return cityService.findCity(code);
    }
}
