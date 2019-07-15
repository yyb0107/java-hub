package com.bingo.springboot.demo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author Bingo
 * @title: ApplicationStart
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/14  23:34
 */
@SpringBootApplication
@EnableAutoDemoConfiguration
public class ApplicationStartDemo3 {
    public static void main(String[] args) {
        ApplicationContext application =  SpringApplication.run(ApplicationStartDemo3.class,args);
        String[] names = application.getBeanDefinitionNames();
        for(String name:names ){
            System.out.println(name);
        }
    }
}
