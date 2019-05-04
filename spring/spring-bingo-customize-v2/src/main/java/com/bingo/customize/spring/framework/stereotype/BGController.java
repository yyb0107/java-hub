package com.bingo.customize.spring.framework.stereotype;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BGController{
    String value() default "";
}
