package com.bingo.java.spring.customize.mvc.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BGService {
    String value() default "";
}
