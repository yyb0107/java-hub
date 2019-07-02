package com.bingo.rpc.server;

import com.bingo.rpc.api.RPCRequest;
import com.bingo.rpc.server.annotation.RPCService;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bingo
 * @title: server
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/27  23:01
 */
//@Component
public class RPCServer implements ApplicationContextAware, InitializingBean {
    private int port ;
    private int registryPort = 8080;
    final Map<String,Object> nameMapService = new HashMap<String,Object>();
    Bootstrap bootstrap;

    public RPCServer(int port) {
        this.port = port;
    }

    public void publisher() throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(boss, work).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
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
                pipeline.addLast("handler", new RequestHandler(nameMapService));
            }
        });
        ChannelFuture future = server.bind(port).sync();
        System.out.println("Provider 启动完成 "+port);
        future.channel().closeFuture().sync();
    }

    public Bootstrap registryBootstrap(ServiceRegistryHandler handler) {
        if (bootstrap != null) {
            return bootstrap;
        }
        EventLoopGroup work = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(work).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
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
                pipeline.addLast("handler",handler );
            }
        });
        return bootstrap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,Object> maps = applicationContext.getBeansWithAnnotation(RPCService.class);
        maps.forEach((key,bean)->{
            Class<?> clazz = bean.getClass();
            nameMapService.put(clazz.getAnnotation(RPCService.class).value(),bean);
            try {
                ServiceRegistryHandler handler = new ServiceRegistryHandler();
                ChannelFuture future = registryBootstrap(handler).connect("127.0.0.1", registryPort).sync();
                future.channel().writeAndFlush(new RPCRequest(RPCRequest.TYPE.REGISTRY,clazz.getName(),null,null,null));
                future.channel().closeFuture().sync();
                System.out.println(handler.getResponse());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void afterPropertiesSet() throws Exception {
        publisher();
    }

//    public static void main(String[] args) {
//
//        RPCServer server = new RPCServer(8080);
//        server.publisher();
//    }


}
