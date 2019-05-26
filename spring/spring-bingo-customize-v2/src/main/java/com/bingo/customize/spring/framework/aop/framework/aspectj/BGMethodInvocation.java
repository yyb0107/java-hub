package com.bingo.customize.spring.framework.aop.framework.aspectj;

import com.bingo.customize.spring.framework.aop.framework.intercept.BGJoinpoint;

import java.lang.reflect.Method;

public interface BGMethodInvocation extends BGJoinpoint {
    Method getMethod();
}
