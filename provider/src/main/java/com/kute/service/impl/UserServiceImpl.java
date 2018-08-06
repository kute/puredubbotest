package com.kute.service.impl;

import com.kute.domain.User;
import com.kute.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * created by kute on 2018/05/13 11:08
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User getUser(Integer id) {
        return new User(id);
    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public User createUser(User user) {
        return null;
    }
}
