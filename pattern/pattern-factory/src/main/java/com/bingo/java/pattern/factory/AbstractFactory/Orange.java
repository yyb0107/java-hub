package com.bingo.java.pattern.factory.AbstractFactory;

/**
 * @author Bingo
 * @title: Orange
 * @projectName java-hub
 */
public class Orange implements IFruit {
    @Override
    public void taste() {
        System.out.println("这是Orange的味道");
    }

}
