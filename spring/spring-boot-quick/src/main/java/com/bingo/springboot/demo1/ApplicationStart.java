package com.bingo.springboot.demo1;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Bingo
 * @title: ApplicationStart
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/14  23:34
 */
@SpringBootApplication
public class ApplicationStart {
    public static void main(String[] args) {
        ApplicationContext application =  SpringApplication.run(ApplicationStart.class,args);
        String[] names = application.getBeanDefinitionNames();
        for(String name:names ){
            System.out.println(name);
        }
    }
}
