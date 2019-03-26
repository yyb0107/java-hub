package com.bingo.java.pattern.singletonpattern;

/**
 * @author Bingo
 * @title: EnumSingletonTest
 * @projectName java-hub
 */
public class EnumSingletonTest {

    public static void main(String[] args) {
        Runnable r1 = () -> {
            EnumSingleton  enumSingleton1 = EnumSingleton.getInstance();
            Object obj1 = new Object();
            System.out.println("obj1 "+obj1);
            enumSingleton1.setObj(obj1);
            System.out.println(String.format("thread[%s] enumSingleton1 : %s,obj: %s",Thread.currentThread().getId(),enumSingleton1.hashCode(),enumSingleton1.getObj()));
        };

        Runnable r2 = () -> {
            EnumSingleton  enumSingleton2 = EnumSingleton.getInstance();
            Object obj2 = new Object();
            System.out.println("obj2 "+obj2);
            enumSingleton2.setObj(obj2);
            System.out.println(String.format("thread[%s] enumSingleton2 : %s,obj: %s",Thread.currentThread().getId(),enumSingleton2.hashCode(),enumSingleton2.getObj()));
        };

        new Thread(r1).start();
        new Thread(r2).start();
    }

}
