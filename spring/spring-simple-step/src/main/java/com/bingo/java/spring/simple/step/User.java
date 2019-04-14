package com.bingo.java.spring.simple.step;

import org.springframework.stereotype.Component;

/**
 * @author Bingo
 * @title: User
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/14  15:31
 */
public class User {

    private String uname;
    private String password;
    private int uid;
    private int sex;

    public User() {
    }

    public User(String uname, String password) {
        this.uname = uname;
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "uname='" + uname + '\'' +
                ", password='" + password + '\'' +
                ", uid=" + uid +
                ", sex=" + sex +
                '}';
    }
}
