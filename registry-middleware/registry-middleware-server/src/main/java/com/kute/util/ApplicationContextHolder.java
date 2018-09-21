package com.kute.util;

import com.google.common.base.Preconditions;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * created by kute on 2018/09/20 17:15
 */
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> aClass) {
        Preconditions.checkNotNull(aClass);
        return applicationContext.getBean(aClass);
    }

    public static <T> T getBean(String beanName, Class<T> aClass) {
        return applicationContext.getBean(beanName, aClass);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
