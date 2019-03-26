package com.bingo.java.pattern.singletonpattern;

/**
 * @author Bingo
 * @title: LazySingletonStrengthen2
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/23  22:10
 */
public class LazySingletonStrengthen2 {
    private LazySingletonStrengthen2() {
    }

    private static LazySingletonStrengthen2 instance = null;

    /**
     * synchronized 语句块
     * double check
     * @return
     */
    public  static LazySingletonStrengthen2 getInstance() {
        if (instance == null) {
            synchronized (instance){
                if(instance == null){
                    instance = new LazySingletonStrengthen2();
                }
            }

        }
        return instance;
    }
}
