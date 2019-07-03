package com.bingo.rpc.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bingo
 * @title: ServiceRegistryHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/2  21:48
 */
@Slf4j
public class ServiceRegistryHandler extends ChannelInboundHandlerAdapter {
    private Object response ;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.response = msg;
        log.debug("Server端收到注册结果"+msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public Object getResponse() {
        return response;
    }
}
