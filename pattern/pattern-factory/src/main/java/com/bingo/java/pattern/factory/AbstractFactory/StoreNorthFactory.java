package com.bingo.java.pattern.factory.AbstractFactory;

/**
 * @author Bingo
 * @title: StoreNorthFactory
 * @projectName java-hub
 * @description: 北方商店生产牛奶冰激凌+苹果
 * @date 2019/3/27  21:41
 */
public class StoreNorthFactory implements StoreFactory{
    @Override
    public IFruit getFruit() {
        return new Apple();
    }

    @Override
    public IIceCream getIceCream() {
        return new MilkIceCream();
    }
}
