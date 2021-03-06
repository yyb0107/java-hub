package com.bingo.rpc.registry;

import com.bingo.rpc.api.RPCRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Bingo
 * @title: RpcRegistryHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/2  20:52
 */
@Slf4j
public class RpcRegistryHandler extends ChannelInboundHandlerAdapter {
    RpcRegistry bootstrap;
    Map<String, RPCRequest> container;
    private Object repsonse;
    public RpcRegistryHandler(Map<String, RPCRequest> container,RpcRegistry bootstrap) {
        this.container = container;
        this.bootstrap = bootstrap;
    }

    public RpcRegistryHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.debug("收到请求 "+msg);
        if (msg instanceof RPCRequest) {
            RPCRequest request = (RPCRequest) msg;
            //向注册容器查询是否存在这项服务，如果存在，则向服务提供者发送请求，如果不存在，直接返回
            String key = request.getClassName();
            if (request.getType() == RPCRequest.TYPE.REGISTRY) {
                this.container.put(key,request);
                log.debug("往注册中心一条记录");
                ctx.writeAndFlush("registry端发送结果：注册成功");
            }else if(request.getType() == RPCRequest.TYPE.REQUEST){
                if(container.containsKey(key)){
                    RPCRequest req = request;
                    RpcRegistryHandler handler = new RpcRegistryHandler();
                    log.debug("向服务提供者发送请求");
                    ChannelFuture future = bootstrap.bootstrap(handler).connect("127.0.0.1",8089).sync();
                    //向服务提供者发送请求
                    future.channel().writeAndFlush(req).sync();
                    future.channel().closeFuture().sync();
//                    bootstrap.shutdownGracefully();
//                    将服务提供者的响应发送个客户端
                    log.debug("向客户端响应发送响应");
                    Object result = handler.getResponse();
                    log.debug(""+result);
                    ctx.writeAndFlush(result);
                }
            }

        }else{
            repsonse = msg;
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public Object getResponse(){
        return repsonse;
    }
}
