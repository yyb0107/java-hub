package com.bingo.java.pattern.singletonpattern;

/**
 * @author Bingo
 * @title: ThreadSingletonTest
 * @projectName java-hub
 */
public class ThreadSingletonTest {
    public static void main(String[] args) throws Exception {
        ConcurrentExecutor.execute(new ConcurrentExecutor.RunHandler() {
            @Override
            public void handler() {
                ThreadSingleton threadSingleton = ThreadSingleton.getInstance();
                System.out.println(String.format("Thread:[%s--%s] , threadSingleton: %s", Thread.currentThread().getId(), Thread.currentThread().getName(), threadSingleton));
                ThreadSingleton threadSingleton2 = ThreadSingleton.getInstance();
                System.out.println(String.format("Thread:[%s--%s] , threadSingleton2: %s", Thread.currentThread().getId(), Thread.currentThread().getName(), threadSingleton2));
            }
        }, 3, 6);
    }
}
