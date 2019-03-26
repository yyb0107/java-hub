package com.bingo.java.pattern.singletonpattern.trouble.serializable;

import java.io.Serializable;

/**
 * @title: InnerClassSingleton
 * @projectName java-hub
 */
public class InnerClassSingletonSerializable implements Serializable {
    private static InnerClassSingletonSerializable instance;

    private InnerClassSingletonSerializable() {

    }

    public static InnerClassSingletonSerializable getInstance() {
        instance = InnerHolder.holder;
        return InnerHolder.holder;
    }

    static class InnerHolder {
        static final InnerClassSingletonSerializable holder = new InnerClassSingletonSerializable();
    }

    /**
     * 避免反序列化的时候影响单例。
     * 反序列化步骤：
     * 1.查看当前类有没有无参构造函数，如果有，则创建
     * 2.查看是否有readResolve方法，如果有，则执行readResolve方法
     * 详细见源码ObjectInputStream.readObject()
     * @return
     */
    public Object readResolve() {
        return instance;
    }

}
