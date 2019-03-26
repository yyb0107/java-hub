package com.bingo.java.pattern.singletonpattern;

import org.junit.Test;

/**
 * @author Bingo
 * @title: LazySingletonTest
 * @projectName java-hub
 * @description: lazy方式再多线程的环境下，可能new出不同的对象，可以从这个例子中打印出来的对象看到这种现象（多尝试几次）
 * @date 2019/3/23  21:57
 */
public class LazySingletonTest {

    public static void main(String[] args) {
        Runnable r1 = () -> {
            LazySingletonStrengthen1 lazySingletonStrengthen1 = LazySingletonStrengthen1.getInstance();
            System.out.println(String.format("thread[%s] lazySingleton1 : %s",Thread.currentThread().getId(),lazySingletonStrengthen1));
        };

        Runnable r2 = () -> {
            LazySingletonStrengthen1 lazySingletonStrengthen2 = LazySingletonStrengthen1.getInstance();
            System.out.println(String.format("thread[%s] lazySingleton1 : %s",Thread.currentThread().getId(),lazySingletonStrengthen2));
        };

        new Thread(r1).start();
        new Thread(r2).start();
//        LazySingletonTest test = new LazySingletonTest();
//        test.test2();
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
