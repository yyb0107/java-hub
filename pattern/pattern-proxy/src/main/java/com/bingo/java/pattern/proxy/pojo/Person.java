package com.bingo.java.pattern.proxy.pojo;

/**
 * @author Bingo
 * @title: Person
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/28  23:03
 */
public class Person implements IPerson {
    public void findJob(String command){
        System.out.println("我在找工作...");
        System.out.println("command..."+command);
    }
}
