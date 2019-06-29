package com.bingo.rpc.server;

import com.bingo.rpc.api.IUserService;
import com.bingo.rpc.api.RPCRequest;
import com.bingo.rpc.server.provider.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Bingo
 * @title: RPCServerHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/27  23:06
 */
public class RPCServerHandler implements Runnable {
    private Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public RPCServerHandler(Socket socket) {
        this.socket = socket;

    }


    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            RPCRequest request = (RPCRequest)ois.readObject();

            Class<?> clazz = Class.forName(request.getClassName());
            if(clazz == IUserService.class){
                clazz = UserService.class;
            }
            Method method = clazz.getMethod(request.getMethodName(),request.getParameterTypes());
            Object obj = clazz.newInstance();
            Object result = method.invoke(obj, request.getArgs());


            oos.writeObject(result);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally{
            try {
                oos.writeObject("");
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
