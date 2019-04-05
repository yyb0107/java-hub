package com.bingo.java.pattern.proxy.bingoproxy;

import com.bingo.java.pattern.proxy.pojo.IPerson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Bingo
 * @title: BGJobHunter
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/5  13:51
 */
public class BGJobHunter implements BGInvocationHandler {
    IPerson target;

    public Object proxy(IPerson target) {
        this.target = target;
        return BGProxy.newProxyInstance(new BGClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = null;
        before();
        object = method.invoke(target, args);
        after();
        return object;
    }

    private void after() {
        System.out.println("BINGO 找到合适的工作了。");
    }

    private void before() {
        System.out.println("BINGO 根据需求搜集信息……");
    }
}
