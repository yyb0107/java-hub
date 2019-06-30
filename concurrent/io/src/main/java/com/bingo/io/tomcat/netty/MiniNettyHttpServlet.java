package com.bingo.io.tomcat.netty;

import com.bingo.io.tomcat.bio.MiniRequest;
import com.bingo.io.tomcat.bio.MiniResponse;

import java.io.IOException;

/**
 * @author Bingo
 * @title: MiniNettyHttpServlet
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  17:05
 */
public interface MiniNettyHttpServlet {
    void doService(MiniNettyRequest request, MiniNettyResponse response) throws IOException;
}
