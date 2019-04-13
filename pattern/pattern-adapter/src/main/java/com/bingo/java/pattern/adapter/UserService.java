package com.bingo.java.pattern.adapter;

/**
 * @author Bingo
 * @title: UserService
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  15:23
 */
public class UserService implements LoginService {
    @Override
    public UserMsg login(String username, String password) {
        return new UserMsg(200,"使用用户名密码登陆",null);
    }
}
