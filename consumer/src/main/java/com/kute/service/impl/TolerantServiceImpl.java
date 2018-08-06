package com.kute.service.impl;

import com.kute.domain.User;
import com.kute.service.ITolerantService;
import org.springframework.stereotype.Service;

/**
 * created by kute on 2018/08/03 19:08
 */
@Service("tolerantService")
public class TolerantServiceImpl implements ITolerantService {

    @Override
    public User getUser(Integer id) {
        User user = new User(id);
        user.setName("tolerant_getUser_name_" + id);
        return user;
    }
}
