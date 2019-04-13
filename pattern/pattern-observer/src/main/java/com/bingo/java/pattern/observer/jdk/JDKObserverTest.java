package com.bingo.java.pattern.observer.jdk;

import java.util.Date;

/**
 * @author Bingo
 * @title: JDKObserverTest
 * @projectName java-hub
 */
public class JDKObserverTest {
    public static void main(String[] args) {

        GPer gper = new GPer();

        Teacher tom = new Teacher("Tom");
        Teacher jerry = new Teacher("jerry");

        gper.addObserver(tom);
        gper.addObserver(jerry);

        gper.publishQuestion(new Question("如何用jdk的方式实现观察者模式", "如何用jdk的方式实现观察者模式", 1001, new Date(), null));
    }
}
