package com.bingo.java.spring.customize.demo.service;

import com.bingo.java.spring.customize.mvc.annotation.BGService;

/**
 * @author Bingo
 * @title: UserService
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/16  23:57
 */
@BGService
public class UserService implements IUserService {
    @Override
    public User getUser(String id) {
        return null;
    }
}
