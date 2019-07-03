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
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Bingo
 * @title: com.bingo.rpc.client.RPCClientInvocationHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/29  10:02
 */
@Slf4j
public class RPCClientInvocationHandler implements InvocationHandler {
    private String host;
    private int port;
    private Object response;
    Bootstrap bootstrap;
    public RPCClientInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private RPCClientInvocationHandler(){}

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = null;
        log.debug("进入invoke方法");
        RPCClientResponseHandler handler = new RPCClientResponseHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        if(bootstrap== null){
            bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>(){
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
                    pipeline.addLast(handler);
                }
            });
        }
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
        future.channel().writeAndFlush(new RPCRequest(method.getDeclaringClass().getSimpleName(),method.getName(), args, method.getParameterTypes())).sync();
        future.channel().closeFuture().sync();

        obj = handler.getResponse();

        log.debug("result = "+obj);
        group.shutdownGracefully();
        return obj;
    }


}
