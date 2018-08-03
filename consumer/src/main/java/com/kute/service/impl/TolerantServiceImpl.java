package com.kute.service.impl;

import com.kute.domain.User;
import com.kute.service.ITolerantService;
import org.springframework.stereotype.Service;

/**
 * created by kute on 2018/08/01 20:12
 */
@Service("tolerantService")
public class TolerantServiceImpl implements ITolerantService {
    @Override
    public User getUser(Integer userId) {
        User user = new User(userId);
        user.setName("tolerant_getUser_name_" + userId);
        return user;
    }
}
