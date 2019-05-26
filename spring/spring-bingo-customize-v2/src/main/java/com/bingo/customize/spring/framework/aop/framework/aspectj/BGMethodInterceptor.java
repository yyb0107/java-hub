package com.bingo.customize.spring.framework.aop.framework.aspectj;

/**
 * @author Bingo
 * @title: BGMethodInterceptor
 * @projectName java-hub
 */
public interface BGMethodInterceptor  {
    Object invoke(BGMethodInvocation invocation) throws Throwable;
}
