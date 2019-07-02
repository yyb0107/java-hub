package com.bingo.rpc.server;

import com.bingo.rpc.api.RPCRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Bingo
 * @title: RequestHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/2  21:42
 */
public class RequestHandler extends ChannelInboundHandlerAdapter {
    private Map<String,Object> nameMapService ;

    public RequestHandler(Map<String,Object> nameMapService){
        this.nameMapService = nameMapService;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RPCRequest request = (RPCRequest)msg;

        Class<?> clazz = Class.forName(request.getClassName());
        Object obj = nameMapService.get(clazz.getSimpleName());
        if(obj == null){
            return ;
        }
        Method method = clazz.getMethod(request.getMethodName(),request.getParameterTypes());
        Object result = method.invoke(obj, request.getArgs());


        ctx.writeAndFlush(result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
