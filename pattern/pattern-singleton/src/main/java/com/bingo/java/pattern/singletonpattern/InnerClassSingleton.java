package com.bingo.java.pattern.singletonpattern;
/**
 * @title: InnerClassSingleton
 * @projectName java-hub
 */
public class InnerClassSingleton {
    private InnerClassSingleton() {
        if(InnerHolder.holder!=null){
            throw new RuntimeException("该实例已经存在，单例模式下禁止创建多个实例");
        }
    }

    public static InnerClassSingleton getInstance() {
        return InnerHolder.holder;
    }

    static class InnerHolder {
        static final InnerClassSingleton holder = new InnerClassSingleton();
    }
}
