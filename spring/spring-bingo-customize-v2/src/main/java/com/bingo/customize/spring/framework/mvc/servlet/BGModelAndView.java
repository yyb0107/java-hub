package com.bingo.customize.spring.framework.mvc.servlet;

import java.util.HashMap;
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
    private Map<String,Object> model;

    public BGModelAndView() {
    }

    public BGModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String,Object> getModel() {
        if(model == null){
            model = new HashMap<>();
        }
        return model;
    }

    public void add(String key,Object obj){
        getModel().put(key,obj);
    }
}
