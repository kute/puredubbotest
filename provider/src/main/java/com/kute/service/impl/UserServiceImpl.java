package com.kute.service.impl;

import com.kute.cache.LocalCacheUtil;
import com.kute.domain.User;
import com.kute.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * created by kute on 2018/05/13 11:08
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User getUser(Integer id) {
        try {
            return LocalCacheUtil.get(id);
        } catch (ExecutionException e) {
            logger.error("getUser error, userid={}", id, e);
        }
        return null;
    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public User createUser(User user) {
        return null;
    }
}
