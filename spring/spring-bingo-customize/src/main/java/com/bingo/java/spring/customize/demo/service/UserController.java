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

    @BGRequestMapping("/add")
    public String add( @BGRequestParam("a")String a,@BGRequestParam("b")String b){
        return "The Result Is "+(Integer.parseInt(a)+Integer.parseInt(b));
    }

    @BGRequestMapping("/sub")
    public String sub( @BGRequestParam("a")Integer a,@BGRequestParam("b")Integer b){
        return "The Result Is "+(a-b);
    }

    public void setService(UserService service) {
        this.service = service;
    }
}
