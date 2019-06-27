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

    ExecutorService executorService = Executors.newCachedThreadPool();

    public void publisher(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new RPCServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
