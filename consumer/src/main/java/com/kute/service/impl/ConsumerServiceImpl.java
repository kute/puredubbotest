package com.kute.service.impl;

import com.kute.domain.User;
import com.kute.service.IConsumerService;
import com.kute.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * created by kute on 2018/05/14 01:36
 */
@Service("consumerService")
public class ConsumerServiceImpl implements IConsumerService {

    @Resource
    private IUserService userService;

    @Override
    public User getUser(Integer userId) {
        return userService.getUser(userId);
    }
}
