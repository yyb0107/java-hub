package com.bingo.rpc.client;

import com.bingo.rpc.api.RPCRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Bingo
 * @title: com.bingo.rpc.client.RPCClientHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/29  10:02
 */
public class RPCClientHandler implements InvocationHandler {
    private String host;
    private int port;
    private Object response;
    Bootstrap bootstrap;
    public RPCClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private RPCClientHandler(){}

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = null;
        Handler handler = new Handler();
        if(bootstrap== null){
            bootstrap = new Bootstrap();
            bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>(){
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                    //自定义协议编码器
                    pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                    //对象参数类型编码器
                    pipeline.addLast("encoder", new ObjectEncoder());
                    //对象参数类型解码器
                    pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                    pipeline.addLast();
                }
            });
        }
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
        future.channel().writeAndFlush(new RPCRequest(method.getDeclaringClass().getName(),method.getName(), args, method.getParameterTypes())).sync();
        future.channel().closeFuture().sync();
        obj = handler.getResponse();
        return obj;
    }


}
