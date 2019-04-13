package com.bingo.java.pattern.observer.guava;

import com.google.common.eventbus.EventBus;

import java.util.Observable;

/**
 * @author Bingo
 * @title: GPer
 * @projectName java-hub
 */
public class GPer extends EventBus {
    private String name = "GPer生态圈";

    public void publishQuestion(Question question){
        System.out.println("刚刚有人提了一个新问题");
        post(question);
//        setChanged();
//        notifyObservers(question);
    }
}
