package com.bingo.rpc.server;

import com.bingo.rpc.server.annotation.RPCService;
import com.bingo.rpc.server.zkregistry.ZkRegistry;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: server
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/27  23:01
 */
//@Component
//ps.这里的@Component可以不用声明，因为在Configuration类中，使用@Bean的方式会创建一个bean，默认情况下，在这里声明@Component会产生定义两次的问题，但是在@Bean中指定了name=RPCServer,这样@Component的这个会被忽略
@Slf4j
public class RPCServer implements ApplicationContextAware, InitializingBean {
    private int port;
//    private int registryPort = 8080;
    final Map<String, Object> nameMapService = new HashMap<String, Object>();
    Bootstrap bootstrap;
    ServerBootstrap server;

    public RPCServer(int port) {
        this.port = port;
    }

    public void publisher() throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        server = new ServerBootstrap();
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
                pipeline.addLast("handler", new ServiceRequestHandler(nameMapService));
            }
        });
        ChannelFuture future = server.bind(port).sync();
        log.debug("Provider 启动完成 " + port);
//        future.channel().closeFuture().sync();
    }

    /**
     * 使用zk后，不再使用这么愚蠢的方式进行服务注册了
     *
     * @param handler
     * @return
     */
    @Deprecated
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
                pipeline.addLast("handler", handler);
            }
        });
        return bootstrap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> maps = applicationContext.getBeansWithAnnotation(RPCService.class);
        maps.forEach((key, bean) -> {
            Class<?> clazz = bean.getClass();
            nameMapService.put(clazz.getAnnotation(RPCService.class).value(), bean);
            ServiceRegistryHandler handler = new ServiceRegistryHandler();
//                这里进行服务注册的逻辑
//                ChannelFuture future = registryBootstrap(handler).connect("127.0.0.1", registryPort).sync();
//                future.channel().writeAndFlush(new RPCRequest(RPCRequest.TYPE.REGISTRY,clazz.getAnnotation(RPCService.class).value(),null,null,null));
//                future.channel().closeFuture();
//                log.debug(""+handler.getResponse());
            String serviceName = clazz.getAnnotation(RPCService.class).value();
            String serviceAddress = ZkRegistry.host() + ":" + port;
            ZkRegistry.registry(serviceName, serviceAddress);
            log.info("{}/{} registry success.",serviceName,serviceAddress);
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
