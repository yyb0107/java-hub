package com.bingo.java.pattern.factory.simple;

import com.bingo.java.pattern.factory.entity.Apple;
import com.bingo.java.pattern.factory.entity.IFruit;
import com.bingo.java.pattern.factory.entity.Orange;

/**
 * @author Bingo
 * @title: SimpleFactoryTest
 * @projectName java-hub
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {
        SimpleFactoryTest test = new SimpleFactoryTest();
        test.testGetFruitV1();
    }


    public void testGetFruitV1() {
        IFruit fruit = (IFruit) SimpleFactory.getFruitV1("com.bingo.java.pattern.factory.entity.Apple");
        fruit.taste();
        fruit = (IFruit) SimpleFactory.getFruitV1("com.bingo.java.pattern.factory.entity.Orange");
        fruit.taste();
    }


    public void testGetFruitV2() {
        IFruit fruit = SimpleFactory.getFruitV2(Apple.class);
        fruit.taste();
        fruit = SimpleFactory.getFruitV2(Orange.class);
        fruit.taste();
    }
}
