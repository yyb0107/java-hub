package com.bingo.java.pattern.adapter;

import com.bingo.java.pattern.adapter.third.ThirdLoginService;

/**
 * @author Bingo
 * @title: AdapterTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  15:40
 */
public class AdapterTest {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        UserService userService = new UserService();
        UserMsg msg = userService.login("Username","Password");
        System.out.println(msg);

        ThirdLoginService userService1 = new UserServiceExtence();
        UserMsg msg2 = userService1.LoginSina("Sina");
        System.out.println(msg2);

    }
}
