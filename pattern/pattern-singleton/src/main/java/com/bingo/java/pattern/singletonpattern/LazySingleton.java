package com.bingo.java.pattern.singletonpattern;

/**
 * @author Bingo
 * @title: LazySingleton
 */
public class LazySingleton {
    private LazySingleton() {
    }
    private static LazySingleton instance = null;


    /**
     * 使用的时候才开始创建对象，但是在多线程环境下，可能会出现创建多个实例或者实例覆盖的现象
     * @return
     */

    public static LazySingleton getInstance(){
        if(instance == null){
            instance = new LazySingleton();
        }
        return instance;
    }
    /*public synchronized static LazySingleton getInstance(){
        if(instance == null){
            instance = new LazySingleton();
        }
        return instance;
    }*/
}
