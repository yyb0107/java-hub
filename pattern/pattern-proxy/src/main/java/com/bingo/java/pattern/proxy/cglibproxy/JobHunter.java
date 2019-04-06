package com.bingo.java.pattern.proxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Bingo
 * @title: JobHunter
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/5  23:04
 */
public class JobHunter implements MethodInterceptor {
    public Object getInstance(Class clazz) {
        //这里就是生成代理对象的代码
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object obj = methodProxy.invokeSuper(o,objects);
        after();
        return obj;
    }

    private void after() {
        System.out.println("这里是CGLib,找到合适的工作了。");
    }

    private void before() {
        System.out.println("这里是CGLib,根据需求搜集信息……");
    }
}
