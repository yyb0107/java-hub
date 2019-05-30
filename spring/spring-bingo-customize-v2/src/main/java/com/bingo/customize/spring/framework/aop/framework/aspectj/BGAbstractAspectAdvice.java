package com.bingo.customize.spring.framework.aop.framework.aspectj;

import com.bingo.customize.spring.framework.aop.framework.intercept.BGJoinpoint;

import java.lang.reflect.Method;

/**
 * @author Bingo
 * @title: BGAbstractAspectAdvice
 * @projectName java-hub
 * @description: TODO
 * @date 2019/5/27  23:02
 */
public abstract class BGAbstractAspectAdvice {
    private Method aspectMethod;
    private Object aspectObject;

    public BGAbstractAspectAdvice(Method aspectMethod, Object aspectObject) {
        this.aspectMethod = aspectMethod;
        this.aspectObject = aspectObject;
    }

    protected Object invokeAdviceMethod(
            BGJoinpoint joinPoint, Object returnValue, Throwable ex)
            throws Throwable {
        //假设织入方法都没有参数
        return aspectMethod.invoke(aspectObject);
    }

}
