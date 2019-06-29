package com.bingo.rpc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Bingo
 * @title: server
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/27  23:01
 */
public class RPCServer {
    ServerSocket serverSocket = null;
    private int port ;

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
                executorService.execute(new RPCServerHandler(socket));
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

    public static void main(String[] args) {

        RPCServer server = new RPCServer(8080);
        server.publisher();
    }


}
