package com.bingo.io.tomcat.bio;

import java.io.IOException;

/**
 * @author Bingo
 * @title: FirstServlet
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  16:50
 */
public class FirstServlet implements MiniHttpServlet{
    @Override
    public void doService(MiniRequest request, MiniResponse response) throws IOException {
        System.out.println("开始处理请求");
        response.write("process finished");
    }
}
