package com.kute.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface DubboConsumerBeforeInvoke {

    // 接口类
    Class[] serviceClazz();

    // rpc方法名
    String[] method();

    boolean enabled() default true;
}
