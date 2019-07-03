package com.bingo.rpc.server;

import com.bingo.rpc.api.RPCRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Bingo
 * @title: ServiceRequestHandler
 * @projectName java-hub
 * @description: 最终响应请求的地方
 * @date 2019/7/2  21:42
 */
@Slf4j
public class ServiceRequestHandler extends ChannelInboundHandlerAdapter {
    private Map<String,Object> nameMapService ;

    public ServiceRequestHandler(Map<String,Object> nameMapService){
        this.nameMapService = nameMapService;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RPCRequest request = (RPCRequest)msg;

        Object obj = nameMapService.get(request.getClassName());
        if(obj == null){
            return ;
        }
        log.debug(""+obj);
        log.debug(""+request);
        Method method = obj.getClass().getMethod(request.getMethodName(),request.getParameterTypes());
        Object result = method.invoke(obj, request.getArgs());


        ctx.writeAndFlush(result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
