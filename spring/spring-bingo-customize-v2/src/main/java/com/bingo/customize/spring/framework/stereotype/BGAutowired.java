package com.bingo.customize.spring.framework.stereotype;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BGAutowired {
    String value() default "";
}
