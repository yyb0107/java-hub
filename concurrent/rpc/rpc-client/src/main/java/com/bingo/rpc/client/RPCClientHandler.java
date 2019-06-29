package com.bingo.rpc.client;

import com.bingo.rpc.api.RPCRequest;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Bingo
 * @title: com.bingo.rpc.client.RPCClientHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/29  10:02
 */
public class RPCClientHandler implements InvocationHandler {
    private String host;
    private int port;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    public RPCClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket socket = new Socket(host,port);
        oos = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("开始写数据……");
        method.getParameters();
        oos.writeObject(new RPCRequest(method.getDeclaringClass().getName(),method.getName(), args, method.getParameterTypes()));
        ois = new ObjectInputStream(socket.getInputStream());
        Object obj = ois.readObject();
        return obj;
    }
}
