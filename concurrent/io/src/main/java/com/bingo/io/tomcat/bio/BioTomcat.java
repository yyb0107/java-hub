package com.bingo.io.tomcat.bio;

import com.bingo.io.tomcat.WebXmlReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Bingo
 * @title: BioTomcat
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  15:19
 */
public class BioTomcat {
    private WebXmlReader reader;

    volatile boolean  isShutdown = false;

    ExecutorService workPool = Executors.newFixedThreadPool(10);

    public BioTomcat(){
        init();
    }

    public void init(){
        reader = new WebXmlReader();
    }

    public void start(int port) throws IOException {

        ServerSocket server = new ServerSocket(port);

        while(!isShutdown){
            Socket socket = server.accept();
            workPool.execute(new RequestHandler(socket,reader));
        }
    }

    public static void main(String[] args) throws IOException {
        BioTomcat tomcat = new BioTomcat();
        tomcat.start(8080);
    }
}
