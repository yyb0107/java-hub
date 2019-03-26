package com.bingo.java.pattern.singletonpattern;

/**
 * @author Bingo
 * @title: ThreadSingleton
 * @projectName java-hub
 */
public class ThreadSingleton {
    private static ThreadLocal<ThreadSingleton> local = new ThreadLocal() {
        @Override
        protected ThreadSingleton initialValue() {
            return new ThreadSingleton();
        }
    };

    private ThreadSingleton() {
    }

    public static ThreadSingleton getInstance() {
        return local.get();
    }
}
