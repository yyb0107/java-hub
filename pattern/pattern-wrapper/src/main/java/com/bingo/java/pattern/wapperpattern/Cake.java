package com.bingo.java.pattern.wapperpattern;

/**
 * @author Bingo
 * @title: Cake
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  16:20
 */
public class Cake {
    private double price;

    private String name;

    public double getPrice(){
        price = 8.00;
        return price;
    }

    public String getName(){
        name = "原味蛋糕";
        return name;
    }

    public String toString(){
        return String.format("name:%s,价格：%s",getName(),getPrice());
    }



}
