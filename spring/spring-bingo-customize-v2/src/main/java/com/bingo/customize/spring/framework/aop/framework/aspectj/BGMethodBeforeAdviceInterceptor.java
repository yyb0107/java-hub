package com.bingo.customize.spring.framework.aop.framework.aspectj;

import com.bingo.customize.spring.framework.aop.framework.intercept.BGJoinpoint;

import java.lang.reflect.Method;

/**
 * @author Bingo
 * @title: BGMethodBeforeAdviceInterceptor
 * @projectName java-hub
 * @description: TODO
 * @date 2019/5/27  22:20
 */
public class BGMethodBeforeAdviceInterceptor extends BGAbstractAspectAdvice implements  BGAdvice,BGMethodInterceptor {
    private BGJoinpoint invocation;

    public BGMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectObject) {
        super(aspectMethod, aspectObject);
    }

    public void before() throws Throwable {
        super.invokeAdviceMethod(this.invocation,null,null);
    }

    @Override
    public Object invoke(BGJoinpoint invocation) throws Throwable {
        this.invocation = invocation;
        before();
        return invocation.proceed();
    }
}
