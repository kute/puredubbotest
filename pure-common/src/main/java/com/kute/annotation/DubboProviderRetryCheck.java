package com.kute.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface DubboProviderRetryCheck {

    /**
     * redis 过期时间
     * @return
     */
    long expiredMillis() default 3000;

}
