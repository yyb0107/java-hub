package com.bingo.java.pattern.singletonpattern;

/**
 * @author Bingo
 * @title: LazySingletonStrengthen1
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/23  22:10
 */
public class LazySingletonStrengthen1 {
    private LazySingletonStrengthen1() {
    }

    private static LazySingletonStrengthen1 instance = null;

    /**
     * 在方法上 synchronized 关键字
     * @return
     */
    public synchronized static LazySingletonStrengthen1 getInstance() {
        if (instance == null) {
            instance = new LazySingletonStrengthen1();
        }
        return instance;
    }
}
