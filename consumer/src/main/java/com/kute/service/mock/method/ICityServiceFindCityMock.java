package com.kute.service.mock.method;

/**
 * created by kute on 2018/08/03 16:49
 */
public class ICityServiceFindCityMock extends ICityServiceAdapter {

    @Override
    public String findCity(String code, long timeOutMillis) {
        return "mock_findCity_adaptor_" + code;
    }
}
