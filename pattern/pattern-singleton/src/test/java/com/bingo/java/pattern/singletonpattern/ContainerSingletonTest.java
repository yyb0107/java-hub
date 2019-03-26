package com.bingo.java.pattern.singletonpattern;

/**
 * @author Bingo
 * @title: ContainerSingletonTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/24  13:32
 */
public class ContainerSingletonTest {
    public static void main(String[] args) {
        Runnable r1 = () -> {
            Object  containerSingleton1 = ContainerSingleton.getInstance(TestBean.class.getName());
            System.out.println(String.format("thread[%s] enumSingleton1 : %s",Thread.currentThread().getId(),containerSingleton1));
        };

        Runnable r2 = () -> {
            Object  containerSingleton2 = ContainerSingleton.getInstance(TestBean.class.getName());
            System.out.println(String.format("thread[%s] enumSingleton2 : %s",Thread.currentThread().getId(),containerSingleton2));
        };

        new Thread(r1).start();
        new Thread(r2).start();
    }
}
