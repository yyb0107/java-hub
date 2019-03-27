package com.bingo.java.pattern.factory.AbstractFactory;

/**
 * @author Bingo
 * @title: MainFactory
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/27  21:45
 */
public class MainFactory {
    public static void main(String[] args) {
        StoreFactory factory = new StoreNorthFactory();
        factory.getFruit().taste();
        factory.getIceCream().sweet();

        factory = new StoreSouthFactory();
        factory.getFruit().taste();
        factory.getIceCream().sweet();
    }
}
