package com.kute.service;

import com.kute.domain.User;

/**
 * created by kute on 2018/05/14 01:35
 */
public interface IConsumerService {


    User getUser(Integer userId);

    String findCity(String code, long timeOutMillis);

    String buildCity(String code);

}
