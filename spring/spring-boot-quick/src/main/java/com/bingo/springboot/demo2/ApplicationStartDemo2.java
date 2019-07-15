package com.bingo.springboot.demo2;

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
public class ApplicationStartDemo2 {
    public static void main(String[] args) {
        ApplicationContext application =  SpringApplication.run(ApplicationStartDemo2.class,args);
        String[] names = application.getBeanDefinitionNames();
        for(String name:names ){
            System.out.println(name);
        }
    }
}
