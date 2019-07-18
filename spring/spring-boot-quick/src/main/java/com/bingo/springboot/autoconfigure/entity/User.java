package com.bingo.springboot.autoconfigure.entity;

/**
 * @author Bingo
 * @title: User
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/17  23:20
 */
public class User {
    String userName;
    String userId;
    int userAge;
    String address;

    public User() {
    }

    public User(String userName, String userId, int userAge, String address) {
        this.userName = userName;
        this.userId = userId;
        this.userAge = userAge;
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
