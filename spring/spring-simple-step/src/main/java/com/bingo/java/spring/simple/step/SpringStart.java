package com.bingo.java.spring.simple.step;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Bingo
 * @title: com.bingo.java.SpringStart
 * @projectName java-hub
 */
@Configuration
public class SpringStart {

    public static void main(String[] args) {
        FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext(SpringStart.class.getClassLoader().getResource("config.xml").getFile());
        UserService userService = (UserService)applicationContext.getBean("userService");
        userService.login(new User("uname","password"));

        applicationContext.start();
    }
}
