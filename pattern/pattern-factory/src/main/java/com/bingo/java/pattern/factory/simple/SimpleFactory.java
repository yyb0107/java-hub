package com.bingo.java.pattern.factory.simple;

import com.bingo.java.pattern.factory.entity.Apple;
import com.bingo.java.pattern.factory.entity.IFruit;
import com.bingo.java.pattern.factory.entity.Orange;

/**
 * @author Bingo
 * @title: SimpleFactory
 * @projectName java-hub
 */
public class SimpleFactory {
    public  static IFruit getFruitV0(String name) {
        IFruit fruit = null;
        if("Apple".equals(name)){
            fruit = new Apple();
        }else if("Orange".equals(name)){
            fruit = new Orange();
        }

        return fruit;
    }

    public static Object getFruitV1(String className) {
        try {
            Class clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public  static <T extends IFruit> T getFruitV2(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

}

