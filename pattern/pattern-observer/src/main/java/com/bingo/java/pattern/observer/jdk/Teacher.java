package com.bingo.java.pattern.observer.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Bingo
 * @title: Teacher
 * @projectName java-hub
 */
public class Teacher implements Observer {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Question) {
            Question q = (Question) arg;
            System.out.println(String.format("%s老师收到一个新问题【%s】，问题如下：", this.getName(), q.getTitle()));
            System.out.println(String.format("%s ",q.getTitle()));
        }
    }

    public String getName() {
        return this.name;
    }
}
