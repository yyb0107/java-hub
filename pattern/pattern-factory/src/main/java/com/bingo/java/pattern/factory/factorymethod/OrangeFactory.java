package com.bingo.java.pattern.factory.factorymethod;

import com.bingo.java.pattern.factory.entity.IFruit;
import com.bingo.java.pattern.factory.entity.Orange;

public class OrangeFactory implements IFruitFactory {
    @Override
    public IFruit getFruit() {
        return new Orange();
    }
}
