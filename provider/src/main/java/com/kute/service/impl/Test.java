package com.kute.service.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * created by kute on 2018/08/05 17:08
 */
public class Test {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-core.xml");

        CityServiceImpl cityService = (CityServiceImpl) context.getBean("cityService");

        cityService.liveCity("test", 6000);




    }
}
