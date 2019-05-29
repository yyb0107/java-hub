package com.bingo.customize.spring.framework.aop.framework;

import com.bingo.customize.spring.framework.aop.framework.aspectj.BGReflectiveMethodInvocation;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author Bingo
 * @title: BGJdkDynamicAopProxy
 * @projectName java-hub
 */
final class BGJdkDynamicAopProxy implements BGAopProxy, InvocationHandler, Serializable {
    /**
     * Config used to configure this proxy.
     */
    private final BGAdvisedSupport advised;

    public BGJdkDynamicAopProxy(BGAdvisedSupport config) throws Exception {
//        if (config.getAdvisors().length == 0 && config.getTargetSource() == AdvisedSupport.EMPTY_TARGET_SOURCE) {
//            throw new AopConfigException("No advisors and no TargetSource specified");
//        }
        this.advised = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method,this.advised.getTargetClass());
        BGReflectiveMethodInvocation invocation = new BGReflectiveMethodInvocation(proxy,this.advised.getTarget(),method,args,this.advised.getTargetClass(),interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }

    @Override
    public Object getProxy() {
        return getProxy(advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, advised.getTargetClass().getInterfaces(), this);
    }
}
