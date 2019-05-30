package com.bingo.customize.spring.framework.aop.framework.aspectj;

import com.bingo.customize.spring.framework.aop.framework.intercept.BGJoinpoint;

import java.lang.reflect.Method;

/**
 * @author Bingo
 * @title: BGAfterReturningAdviceInterceptor
 * @projectName java-hub
 */
public class BGAroundAdviceInterceptor extends BGAbstractAspectAdvice implements BGAdvice, BGMethodInterceptor {

    private BGJoinpoint invocation;

    public BGAroundAdviceInterceptor(Method aspectMethod, Object aspectObject) {
        super(aspectMethod, aspectObject);
    }

    @Override
    public Object invoke(BGJoinpoint invocation) throws Throwable {
        this.invocation = invocation;
        this.around(null,invocation.getMethod(),invocation.getArguments(),invocation.getThis());
        Object retVal = invocation.proceed();
        this.around(retVal,invocation.getMethod(),invocation.getArguments(),invocation.getThis());
        return retVal;
    }

    private void around(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.invocation,retVal,null);
    }
}
