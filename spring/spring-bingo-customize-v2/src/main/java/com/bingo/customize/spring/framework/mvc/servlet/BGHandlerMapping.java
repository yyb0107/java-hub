package com.bingo.customize.spring.framework.mvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author Bingo
 * @title: BGHandlerMapping
 * @projectName java-hub
 */
public class BGHandlerMapping {
    private Pattern pattern;
    private Object defaultHandler;//controller
    private Method method;

    public BGHandlerMapping(Pattern pattern, Object defaultHandler, Method method) {
        this.pattern = pattern;
        this.defaultHandler = defaultHandler;
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Object getDefaultHandler() {
        return defaultHandler;
    }

    public void setDefaultHandler(Object defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
