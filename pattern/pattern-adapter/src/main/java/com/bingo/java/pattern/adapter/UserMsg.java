package com.bingo.java.pattern.adapter;

/**
 * @author Bingo
 * @title: UserMsg
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  15:22
 */
public class UserMsg {

    int code;
    String message;
    String errorMessage;


    public UserMsg(int code, String message, String errorMessage) {
        this.code = code;
        this.message = message;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "UserMsg{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
