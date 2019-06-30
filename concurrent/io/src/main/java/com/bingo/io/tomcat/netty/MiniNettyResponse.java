package com.bingo.io.tomcat.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Bingo
 * @title: MiniNettyResponse
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  16:59
 */
public class MiniNettyResponse {
    private ChannelHandlerContext context;
    private HttpRequest req;

    public MiniNettyResponse(ChannelHandlerContext ctx, HttpRequest req) {
        this.context = ctx;
        this.req = req;
    }

    public void write(String content) throws IOException {
        // 设置 http协议及请求头信息
        FullHttpResponse response = new DefaultFullHttpResponse(
                // 设置http版本为1.1
                HttpVersion.HTTP_1_1,
                // 设置响应状态码
                HttpResponseStatus.OK,
                // 将输出值写出 编码为UTF-8
                Unpooled.wrappedBuffer(content.getBytes("UTF-8")));

        response.headers().set("Content-Type", "text/html;");
        context.write(response);
        context.flush();
        context.close();
    }

    public void close() throws IOException {
        context.close();
    }
}
