package com.bingo.io.tomcat.bio;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Bingo
 * @title: MiniNettyResponse
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  16:59
 */
public class MiniResponse {
    OutputStream outputStream ;
    public MiniResponse(OutputStream outputStream) {
this.outputStream = outputStream;
    }

    public void write(String content) throws IOException {
        outputStream.write(content.getBytes());
    }

    public void close() throws IOException {
        if(outputStream != null){
            outputStream.close();
        }
    }
}
