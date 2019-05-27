package com.bingo.customize.spring.framework.aop.framework.aspectj;

import com.bingo.customize.spring.framework.aop.framework.intercept.BGJoinpoint;

import java.lang.reflect.Method;

/**
 * @author Bingo
 * @title: BGAfterReturningAdviceInterceptor
 * @projectName java-hub
 * @description: TODO
 * @date 2019/5/27  22:17
 */
public class BGAfterReturningAdviceInterceptor extends BGAbstractAspectAdvice implements BGAdvice, BGMethodInterceptor {

    private BGJoinpoint invocation;

    public BGAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectObject) {
        super(aspectMethod, aspectObject);
    }

    @Override
    public Object invoke(BGJoinpoint invocation) throws Throwable {
        Object retVal = invocation.proceed();
        this.invocation = invocation;
        this.afterReturning(retVal,invocation.getMethod(),invocation.getArguments(),invocation.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.invocation,retVal,null);
    }
}
