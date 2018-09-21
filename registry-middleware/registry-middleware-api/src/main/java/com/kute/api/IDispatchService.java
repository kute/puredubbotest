package com.kute.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * created by kute on 2018/09/20 17:05
 */
public interface IDispatchService {


    Object dispatch(String beanName, String methodName, Map<String, Object> paramMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

}
