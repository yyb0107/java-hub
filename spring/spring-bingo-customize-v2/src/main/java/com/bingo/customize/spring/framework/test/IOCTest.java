package com.bingo.customize.spring.framework.test;

import com.bingo.customize.spring.framework.context.BGApplicationContext;

/**
 * @author Bingo
 * @title: IOCTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/5/6  22:50
 */
public class IOCTest {
    public static void main(String[] args) {
        BGApplicationContext applicationContext = new BGApplicationContext("application.properties");
        Object obj = applicationContext.getBean("userController");
        System.out.println(obj);
    }
}
