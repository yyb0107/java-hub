package com.bingo.customize.spring.framework.demo.service;

import com.bingo.customize.spring.framework.mvc.servlet.BGModelAndView;
import com.bingo.customize.spring.framework.stereotype.BGController;
import com.bingo.customize.spring.framework.stereotype.BGRequestMapping;
import com.bingo.customize.spring.framework.stereotype.BGRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bingo
 * @title: PageController
 * @projectName java-hub
 */
@BGController("page")
@BGRequestMapping("/page")
public class PageController {
    @BGRequestMapping("/unfound")
    public BGModelAndView showName(HttpServletRequest req, HttpServletResponse resp, @BGRequestParam("name")String name){
        BGModelAndView modelAndView = new BGModelAndView();
        modelAndView.setViewName("404");
        modelAndView.add("message", "just a test");
        return modelAndView;
    }
}
