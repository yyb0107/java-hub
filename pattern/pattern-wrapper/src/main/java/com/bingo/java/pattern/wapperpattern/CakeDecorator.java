package com.bingo.java.pattern.wapperpattern;

/**
 * @author Bingo
 * @title: CakeDecorator
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  16:51
 */
public abstract class CakeDecorator extends Cake{
    Cake cake;

    public CakeDecorator(Cake cake) {
        this.cake = cake;
    }

    public String toString() {
        return String.format("name: %s,price : %s", getName(),getPrice());
    }

    public double getPrice(){
        return cake.getPrice();
    }

    public String getName(){
        return cake.getName();
    }
}
