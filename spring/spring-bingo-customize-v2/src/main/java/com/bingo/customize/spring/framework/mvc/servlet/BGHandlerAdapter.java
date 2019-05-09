package com.bingo.customize.spring.framework.mvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bingo
 * @title: BGHandlerAdapter
 * @projectName java-hub
 */
public class BGHandlerAdapter {
    boolean supports(Object handler) {
        return handler instanceof BGHandlerAdapter;
    }

    BGModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return null;
    }
}
