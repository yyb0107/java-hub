package com.bingo.java.pattern.observer.jdk;

import java.util.Observable;

/**
 * @author Bingo
 * @title: GPer
 * @projectName java-hub
 */
public class GPer extends Observable {
    private String name = "GPer生态圈";

    public void publishQuestion(Question question){
        System.out.println("刚刚有人提了一个新问题");
        setChanged();
        notifyObservers(question);
    }
}
