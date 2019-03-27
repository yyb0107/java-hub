package com.bingo.java.pattern.factory.factorymethod;

import com.bingo.java.pattern.factory.entity.Apple;
import com.bingo.java.pattern.factory.entity.IFruit;

/**
 * @author Bingo
 * @title: AppleFactory
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/27  21:07
 */
public class AppleFactory implements IFruitFactory {
    @Override
    public IFruit getFruit() {
        return new Apple();
    }
}
