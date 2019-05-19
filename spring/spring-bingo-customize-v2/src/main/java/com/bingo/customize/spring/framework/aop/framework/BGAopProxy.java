package com.bingo.customize.spring.framework.aop.framework;

/**
 * @author Bingo
 * @title: BGAopProxy
 * @projectName java-hub
 */
public interface BGAopProxy {
    Object getProxy();

    Object getProxy(ClassLoader classLoader);

}
