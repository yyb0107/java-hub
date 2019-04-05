package com.bingo.java.pattern.proxy.bingoproxy;

import java.lang.reflect.Method;

public interface BGInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
