package com.bingo.io.tomcat.bio;

import java.io.IOException;

/**
 * @author Bingo
 * @title: MiniNettyHttpServlet
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  17:05
 */
public interface MiniHttpServlet {
    void doService(MiniRequest request, MiniResponse response) throws IOException;
}
