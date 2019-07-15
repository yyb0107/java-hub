package com.bingo.springboot.demo3;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({DemoSelector.class,DemoRegistrar.class})
public @interface EnableAutoDemoConfiguration {

}