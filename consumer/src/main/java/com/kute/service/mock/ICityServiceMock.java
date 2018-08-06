package com.kute.service.mock;

import com.kute.service.ICityService;

/**
 * created by kute on 2018/08/03 16:04
 */
public class ICityServiceMock implements ICityService {
    @Override
    public String findCity(String code, long timeOutMillis) {
        return "mock_findCity_" + code;
    }

    @Override
    public String buildCity(String code) {
        return "mock_buildCity_" + code;
    }

    @Override
    public String liveCity(String code, long timeOutMillis) {
        return "mock_liveCity_" + code;
    }
}
