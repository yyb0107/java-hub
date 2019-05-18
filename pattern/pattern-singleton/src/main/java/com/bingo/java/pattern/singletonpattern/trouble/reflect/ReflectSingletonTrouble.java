package com.bingo.java.pattern.singletonpattern.trouble.reflect;

import com.bingo.java.pattern.singletonpattern.ContainerSingleton;
import com.bingo.java.pattern.singletonpattern.InnerClassSingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Bingo
 * @title: ReflectSingletonTrouble
 * @projectName java-hub
 */
public class ReflectSingletonTrouble {
    //    public static void main(String[] args) {
//        InnerClassSingleton innerClassSingleton1 = InnerClassSingleton.getInstance();
//        System.out.println("innerClassSingleton1 " + innerClassSingleton1);
//        try {
//            Constructor constructor = InnerClassSingleton.class.getDeclaredConstructor(null);
//            constructor.setAccessible(true);
//            InnerClassSingleton innerClassSingleton2 = (InnerClassSingleton)constructor.newInstance();
//            System.out.println("innerClassSingleton2 : "+innerClassSingleton2);
//
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//
//    }
    public static void main(String[] args) {
        InnerClassSingleton innerClassSingleton1 = (InnerClassSingleton)ContainerSingleton.getInstance(InnerClassSingleton.class.getName());
        System.out.println("innerClassSingleton1 " + innerClassSingleton1);
        try {
            Constructor constructor = InnerClassSingleton.class.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            InnerClassSingleton innerClassSingleton2 = (InnerClassSingleton)constructor.newInstance();
            System.out.println("innerClassSingleton2 : "+innerClassSingleton2);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
