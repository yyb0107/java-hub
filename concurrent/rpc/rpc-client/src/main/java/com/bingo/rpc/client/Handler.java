package com.bingo.rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Bingo
 * @title: Handler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/2  23:52
 */
public class Handler  extends ChannelInboundHandlerAdapter {
    private Object response;
    public Object getResponse() {
        return response;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.response = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
