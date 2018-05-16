package com.kute.service;

import com.kute.domain.User;

/**
 * created by kute on 2018/05/13 10:55
 */
public interface IUserService {

    User getUser(Integer id);

    void deleteUser(Integer id);

    User createUser(User user);

}
