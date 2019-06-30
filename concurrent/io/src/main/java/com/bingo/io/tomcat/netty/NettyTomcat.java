package com.bingo.io.tomcat.netty;

import com.bingo.io.tomcat.WebXmlReader;
import com.bingo.io.tomcat.bio.BioTomcat;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Bingo
 * @title: NettyTomcat
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  18:39
 */
public class NettyTomcat {
    private WebXmlReader reader;

    volatile boolean isShutdown = false;

    ExecutorService workPool = Executors.newFixedThreadPool(10);

    public NettyTomcat() {
        init();
    }

    public void init() {
        reader = new WebXmlReader();
    }

    public void start(int port) throws IOException, InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();

        server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel client) throws Exception {
                client.pipeline().addLast(new HttpResponseEncoder());
                client.pipeline().addLast(new HttpRequestDecoder());
                client.pipeline().addLast(new MiniNettyTomcatHandler(reader));
            }
        })
        // 针对主线程的配置 分配线程最大数量 128
        .option(ChannelOption.SO_BACKLOG, 128)
        // 针对子线程的配置 保持长连接
        .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture future = server.bind(port).sync();
        System.out.println("Mini Tomcat 已启动，监听的端口是：" + port);
        future.channel().closeFuture().sync();

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        NettyTomcat tomcat = new NettyTomcat();
        tomcat.start(8080);
    }
}
