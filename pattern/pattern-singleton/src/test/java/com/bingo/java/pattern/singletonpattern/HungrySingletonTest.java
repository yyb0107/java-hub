package com.bingo.java.pattern.singletonpattern;

import org.junit.Test;

/**
 * @author Bingo
 * @title: HungrySingletonTest
 * @projectName java-hub
 */
public class HungrySingletonTest {

    public static void main(String[] args) {
        Runnable r1 = () -> {
            HungrySingleton hungrySingleton = HungrySingleton.getInstance();
            System.out.println(String.format("thread[%s] hungrySingleton : %s",Thread.currentThread().getId(),hungrySingleton));
        };

        Runnable r2 = () -> {
            HungrySingleton hungrySingleton = HungrySingleton.getInstance();
            System.out.println(String.format("thread[%s] hungrySingleton : %s",Thread.currentThread().getId(),hungrySingleton));
        };

        new Thread(r1).start();
        new Thread(r2).start();
    }

    @Test
    public void test2(){
        Runnable r1 = () -> {
            LazySingleton lazySingleton1 = LazySingleton.getInstance();
            System.out.println(String.format("thread[%s] lazySingleton1 : %s",Thread.currentThread().getId(),lazySingleton1));
        };

        Runnable r2 = () -> {
            LazySingleton lazySingleton2 = LazySingleton.getInstance();
            System.out.println(String.format("thread[%s] lazySingleton1 : %s",Thread.currentThread().getId(),lazySingleton2));
        };

        new Thread(r1).start();
        new Thread(r2).start();
    }
}
