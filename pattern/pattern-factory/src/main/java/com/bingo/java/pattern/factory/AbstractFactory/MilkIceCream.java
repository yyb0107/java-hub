package com.bingo.java.pattern.factory.AbstractFactory;

/**
 * @author Bingo
 * @title: MilkIceCream
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/27  21:37
 */
public class MilkIceCream implements IIceCream {
    @Override
    public void sweet() {
        System.out.println("这是牛奶冰激凌，9分甜！");
    }
}
