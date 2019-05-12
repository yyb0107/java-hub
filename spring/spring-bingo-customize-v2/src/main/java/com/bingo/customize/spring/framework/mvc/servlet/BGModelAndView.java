package com.bingo.customize.spring.framework.mvc.servlet;

import java.util.Map;

/**
 * @author Bingo
 * @title: BGModelAndView
 * @projectName java-hub
 * @description: TODO
 * @date 2019/5/9  23:11
 */
public class BGModelAndView {
    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String,Object> getModel() {
        return null;
    }
}
