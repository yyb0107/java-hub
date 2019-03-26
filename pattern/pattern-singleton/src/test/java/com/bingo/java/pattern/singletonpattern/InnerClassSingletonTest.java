package com.bingo.java.pattern.singletonpattern;
/**
 * @title: InnerClassSingletonTest
 * @projectName java-hub
 * @description: TODO
 * @author Bingo
 * @date 2019/3/23  21:29
 */
public class InnerClassSingletonTest {

    public static void main(String[] args) {
        InnerClassSingleton innerClassSingleton = InnerClassSingleton.getInstance();
        InnerClassSingleton innerClassSingleton1 = InnerClassSingleton.getInstance();

        System.out.println("innerClassSingleton: "+innerClassSingleton);
        System.out.println("innerClassSingleton1: "+innerClassSingleton1);
    }



}
