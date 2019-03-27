package com.bingo.java.pattern.factory.AbstractFactory;

/**
 * @author Bingo
 * @title: CoffeeIceCream
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/27  21:36
 */
public class CoffeeIceCream implements  IIceCream {
    @Override
    public void sweet() {
        System.out.printf("这是咖啡冰激凌，5分甜！");
    }
}
