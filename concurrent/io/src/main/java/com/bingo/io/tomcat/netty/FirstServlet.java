package com.bingo.io.tomcat.netty;

import com.bingo.io.tomcat.bio.MiniRequest;
import com.bingo.io.tomcat.bio.MiniResponse;

import java.io.IOException;

/**
 * @author Bingo
 * @title: FirstServlet
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  16:50
 */
public class FirstServlet implements MiniNettyHttpServlet {
    @Override
    public void doService(MiniNettyRequest request, MiniNettyResponse response) throws IOException {
        System.out.println("Netty Version 开始处理请求");
        response.write("process finished");
    }
}
