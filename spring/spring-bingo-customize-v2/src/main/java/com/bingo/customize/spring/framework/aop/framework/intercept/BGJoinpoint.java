package com.bingo.customize.spring.framework.aop.framework.intercept;

import java.lang.reflect.Method;

public interface BGJoinpoint {
    Object proceed() throws Throwable;

    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
