package com.bingo.customize.spring.framework.demo.service;


import com.bingo.customize.spring.framework.stereotype.BGAutowired;
import com.bingo.customize.spring.framework.stereotype.BGController;
import com.bingo.customize.spring.framework.stereotype.BGRequestMapping;
import com.bingo.customize.spring.framework.stereotype.BGRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bingo
 * @title: UserController
 * @projectName java-hub
 */
@BGController("user")
@BGRequestMapping("/user")
public class UserController{
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
