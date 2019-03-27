package com.bingo.java.pattern.factory.factorymethod;

import com.bingo.java.pattern.factory.entity.IFruit;

/**
 * @author Bingo
 * @title: FactoryMethodFactory
 * @projectName java-hub
 */
public class FactoryMethodFactory {
    public static void main(String[] args) {
        IFruitFactory factory = new AppleFactory();
        IFruit fruit = factory.getFruit();
        fruit.taste();

        factory = new OrangeFactory();
        fruit = factory.getFruit();
        fruit.taste();
    }
}
