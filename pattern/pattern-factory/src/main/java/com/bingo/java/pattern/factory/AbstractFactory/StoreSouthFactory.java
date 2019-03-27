package com.bingo.java.pattern.factory.AbstractFactory;

/**
 * @author Bingo
 * @title: StoreSouthFactory
 * @projectName java-hub
 * @description: 南方的商店生产咖啡冰激凌+橘子
 * @date 2019/3/27  21:41
 */
public class StoreSouthFactory implements StoreFactory {
    @Override
    public IFruit getFruit() {
        return new Orange();
    }

    @Override
    public IIceCream getIceCream() {
        return new CoffeeIceCream();
    }
}
