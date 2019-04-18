package com.bingo.java.spring.customize.mvc.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BGRequestParam {
    String value() default "";
}
