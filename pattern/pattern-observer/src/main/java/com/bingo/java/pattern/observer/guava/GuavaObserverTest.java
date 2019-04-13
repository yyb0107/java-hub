package com.bingo.java.pattern.observer.guava;


import com.google.common.eventbus.EventBus;

import java.util.Date;

/**
 * @author Bingo
 * @title: JDKObserverTest
 * @projectName java-hub
 */
public class GuavaObserverTest {
    public static void main(String[] args) {

        GPer gper = new GPer();

        Teacher tom = new Teacher("Tom");
        Teacher jerry = new Teacher("jerry");

        gper.register(tom);
        gper.register(jerry);

        gper.publishQuestion(new Question("如何用Guava的方式实现观察者模式", "如何用Guava的方式实现观察者模式", 1001, new Date(), null));
    }
}
