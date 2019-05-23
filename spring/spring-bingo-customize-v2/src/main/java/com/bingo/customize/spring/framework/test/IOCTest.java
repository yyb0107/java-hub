package com.bingo.customize.spring.framework.test;

import com.bingo.customize.spring.framework.context.BGApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * @author Bingo
 * @title: IOCTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/5/6  22:50
 */
public class IOCTest {
    public static void main(String[] args) throws ClassNotFoundException {
//        BGApplicationContext applicationContext = new BGApplicationContext("application.properties");
//        Object obj = applicationContext.getBean("userController");
//        System.out.println(obj);
        Class clazz = Class.forName(BGApplicationContext.class.getName());
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println(Arrays.toString(methods));
        for(Method method:methods){
            String modifier = Modifier.toString(method.getModifiers());
            String returnName = method.getReturnType().getSimpleName();
            method.getAnnotatedReturnType();
//            method.getName();
//            System.out.println(method.getAnnotatedReturnType().getType().getTypeName());
//            System.out.println(String.format("%s %s %s",modifier,returnName,method.getName()));
            System.out.println(method.toString());
        }

        System.out.println(BGApplicationContext.class.getSimpleName());
    }
}
