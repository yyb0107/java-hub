package com.bingo.customize.spring.framework.aop.framework.aspectj;

import com.bingo.customize.spring.framework.aop.framework.intercept.BGJoinpoint;

/**
 * @author Bingo
 * @title: BGMethodInterceptor
 * @projectName java-hub
 */
public interface BGMethodInterceptor  {
    Object invoke(BGJoinpoint invocation) throws Throwable;
}
