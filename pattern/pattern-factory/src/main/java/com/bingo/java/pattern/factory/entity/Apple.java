package com.bingo.java.pattern.factory.entity;

/**
 * @author Bingo
 * @title: Apple
 * @projectName java-hub
 */
public class Apple implements IFruit{

    @Override
    public void taste() {
        System.out.println("这是apple的味道");
    }
}
