package com.bingo.java.pattern.wapperpattern;

/**
 * @author Bingo
 * @title: Cake
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  16:20
 */
public class CakeWithFriuts extends CakeDecorator{
    public CakeWithFriuts(Cake cake){
       super(cake);
    }

    public double getPrice(){

        return super.getPrice()+5;
    }

    public String getName(){
        return super.getName()+"-水果";
    }


}
