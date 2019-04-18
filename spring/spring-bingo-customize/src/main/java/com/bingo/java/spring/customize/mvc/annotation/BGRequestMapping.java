package com.bingo.java.spring.customize.mvc.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BGRequestMapping {
    String value() default "";
}
