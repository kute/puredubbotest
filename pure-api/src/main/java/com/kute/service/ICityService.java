package com.kute.service;

/**
 * created by kute on 2018/06/26 21:53
 */
public interface ICityService {

    String findCity(String code, long timeOutMillis);

    String buildCity(String code);

    String liveCity(String code, long timeOutMillis);
}
