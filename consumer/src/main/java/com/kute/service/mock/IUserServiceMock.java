package com.kute.service.mock;

import com.kute.domain.User;
import com.kute.service.IUserService;

/**
 * created by kute on 2018/08/03 16:16
 */
public class IUserServiceMock implements IUserService {
    @Override
    public User getUser(Integer id) {
        User user = new User(id);
        user.setName("mock_getUser_name_" + id);
        return user;
    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public User createUser(User user) {
        return null;
    }
}
