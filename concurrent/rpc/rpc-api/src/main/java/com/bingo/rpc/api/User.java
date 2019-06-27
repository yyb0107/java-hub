package com.bingo.rpc.api;

import java.io.Serializable;

/**
 * @author Bingo
 * @title: User
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/27  23:22
 */
public class User implements Serializable {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
