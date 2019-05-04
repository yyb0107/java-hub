package com.bingo.customize.spring.framework.stereotype;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BGRequestParam {
    String value() default "";
}
