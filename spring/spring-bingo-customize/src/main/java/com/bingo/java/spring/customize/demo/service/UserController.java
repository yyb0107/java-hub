package com.bingo.java.spring.customize.demo.service;

import com.bingo.java.spring.customize.mvc.annotation.BGAutowired;
import com.bingo.java.spring.customize.mvc.annotation.BGController;
import com.bingo.java.spring.customize.mvc.annotation.BGRequestMapping;
import com.bingo.java.spring.customize.mvc.annotation.BGRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bingo
 * @title: UserController
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/16  23:52
 */
@BGController("user")
@BGRequestMapping("/user")
public class UserController {
    @BGAutowired
    private UserService service;

    @BGRequestMapping("/name")
    public String showName(HttpServletRequest req, HttpServletResponse resp, @BGRequestParam("name")String name){
        return "The Name Is "+name;
    }

    public void setService(UserService service) {
        this.service = service;
    }
}
