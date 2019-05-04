package com.bingo.customize.spring.framework.stereotype;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BGRequestMapping {
    String value() default "";
}
