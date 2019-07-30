package com.bingo.rpc.client;

import java.lang.reflect.Proxy;

/**
 * @author Bingo
 * @title: com.bingo.rpc.client.RPCProxy
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/29  9:57
 */
public class RPCProxy {

    public static <T> T proxy(Class<T> clazz){

        T obj = (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new RPCClientInvocationHandler());
        return obj;
    }

}
