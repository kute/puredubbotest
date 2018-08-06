package com.kute.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface DubboRetryCheck {

    int timeOutMillis() default 3000;
}
