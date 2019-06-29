package com.bingo.rpc.server;

import com.bingo.rpc.server.annotation.RPCService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Bingo
 * @title: server
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/27  23:01
 */
//@Component
public class RPCServer implements ApplicationContextAware, InitializingBean {
    ServerSocket serverSocket = null;
    private int port ;
    final Map<String,Object> nameMapService = new HashMap<String,Object>();

    public RPCServer(int port) {
        this.port = port;
    }

    ExecutorService executorService = Executors.newCachedThreadPool();

    public void publisher() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务发布成功……");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("收到一个请求");
                executorService.execute(new RPCServerHandler(socket,nameMapService));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,Object> maps = applicationContext.getBeansWithAnnotation(RPCService.class);
        maps.forEach((key,bean)->{
            nameMapService.put(bean.getClass().getAnnotation(RPCService.class).value(),bean);
        });


    }

    @Override
    public void afterPropertiesSet() throws Exception {
        publisher();
    }

//    public static void main(String[] args) {
//
//        RPCServer server = new RPCServer(8080);
//        server.publisher();
//    }


}
