package com.bingo.customize.spring.framework.aop.framework.aspectj;

import com.bingo.customize.spring.framework.aop.framework.intercept.BGJoinpoint;

import java.lang.reflect.Method;

/**
 * @author Bingo
 * @title: BGAfterThrowingAdviceInterceptor
 * @projectName java-hub
 * @description: TODO
 * @date 2019/5/27  22:19
 */
public class BGAfterThrowingAdviceInterceptor extends BGAbstractAspectAdvice implements BGAdvice, BGMethodInterceptor {
    private BGJoinpoint invocation;

    public BGAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectObject) {
        super(aspectMethod, aspectObject);
    }

    @Override
    public Object invoke(BGJoinpoint invocation) throws Throwable {
        try {
            invocation.proceed();
        } catch (Throwable ex) {
            invokeAdviceMethod(invocation,null,ex.getCause());
            throw ex;
        }

        return null;
    }
}
