package com.kute.service.mock.method;

import com.kute.service.ICityService;

/**
 * created by kute on 2018/08/03 16:45
 */
public class ICityServiceAdapter implements ICityService {
    @Override
    public String findCity(String code, long timeOutMillis) {
        return null;
    }

    @Override
    public String buildCity(String code) {
        return null;
    }

    @Override
    public String liveCity(String code, long timeOutMillis) {
        return null;
    }
}
