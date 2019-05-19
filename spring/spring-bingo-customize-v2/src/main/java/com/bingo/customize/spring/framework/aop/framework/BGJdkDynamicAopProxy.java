package com.bingo.customize.spring.framework.aop.framework;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
        return null;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
