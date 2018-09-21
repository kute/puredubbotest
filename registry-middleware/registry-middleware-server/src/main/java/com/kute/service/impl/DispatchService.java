package com.kute.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kute.api.IDispatchService;
import com.kute.exception.MiddlewareException;
import com.kute.util.ApplicationContextHolder;
import com.kute.util.ParamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * created by kute on 2018/09/20 17:06
 */
@Component
@Service(interfaceClass = IDispatchService.class, group = "${dubbo.module}")
public class DispatchService implements IDispatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatchService.class);

    @Override
    public Object dispatch(String beanName, String methodName, Map<String, Object> paramMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Object beanService = ApplicationContextHolder.getBean(beanName);
        if(null == beanService) {
            throw new MiddlewareException("Bean[" + beanName + "] is not found.");
        }

        Method method = ReflectionUtils.findMethod(beanService.getClass(), methodName);
        if(null == method) {
            throw new MiddlewareException("Method[" + methodName + "] of Bean[" + beanName + "] is not exists. ");
        }

        Method sourceMethod = AopUtils.getTargetClass(beanService).getMethod(method.getName(), method.getParameterTypes());

        Object[] params = ParamUtil.resolveArguments(paramMap, sourceMethod);

        Object result = method.invoke(beanService, params);

        return result;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
