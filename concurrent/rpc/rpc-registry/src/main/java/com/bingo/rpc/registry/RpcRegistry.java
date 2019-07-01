package com.bingo.rpc.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Bingo
 * @title: RpcRegistry
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/1  22:41
 */
public class RpcRegistry implements ApplicationContextAware, InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        ServerBootstrap server = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();

        server.group(boss, work).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                //自定义协议解码器
                /** 入参有5个，分别解释如下
                 maxFrameLength：框架的最大长度。如果帧的长度大于此值，则将抛出TooLongFrameException。
                 lengthFieldOffset：长度字段的偏移量：即对应的长度字段在整个消息数据中得位置
                 lengthFieldLength：长度字段的长度：如：长度字段是int型表示，那么这个值就是4（long型就是8）
                 lengthAdjustment：要添加到长度字段值的补偿值
                 initialBytesToStrip：从解码帧中去除的第一个字节数
                 */
                LengthFieldBasedFrameDecoder test = new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4);
                test.handlerAdded(null);
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                //自定义协议编码器
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                //对象参数类型编码器
                pipeline.addLast("encoder", new ObjectEncoder());
                //对象参数类型解码器
                pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

            }
        });

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
