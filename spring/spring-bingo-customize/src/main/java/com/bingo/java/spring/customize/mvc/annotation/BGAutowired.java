package com.bingo.java.spring.customize.mvc.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BGAutowired {
    String value() default "";
}
