package com.bingo.java.pattern.proxy.jdkproxy;

import com.bingo.java.pattern.proxy.pojo.IPerson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Bingo
 * @title: JobHunter
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/28  23:12
 */
public class JobHunter implements InvocationHandler {
    IPerson target;

    public  IPerson getInstance(IPerson target){
        this.target = target;
        return (IPerson)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = null;
        before();
        object = method.invoke(target,args);
        after();
        return object;
    }

    private void after() {
        System.out.println("找到合适的工作了。");
    }

    private void before() {
        System.out.println("根据需求搜集信息……");
    }
}
