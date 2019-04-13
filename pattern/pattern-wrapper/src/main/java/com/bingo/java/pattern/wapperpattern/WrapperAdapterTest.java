package com.bingo.java.pattern.wapperpattern;

/**
 * @author Bingo
 * @title: WrapperAdapterTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  16:26
 */
public class WrapperAdapterTest {
    public static void main(String[] args) {
        Cake cake = new Cake();

        System.out.println("cake: "+cake);

        CakeWithCoffee cakeWithCoffee = new CakeWithCoffee(cake);
        System.out.println("cake:"+cakeWithCoffee);

        CakeWithFriuts cakeWithFriuts = new CakeWithFriuts(cakeWithCoffee);
        System.out.println("cake:"+cakeWithFriuts);
    }
}
