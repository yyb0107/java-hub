package com.bingo.io.tomcat.netty;

import com.bingo.io.tomcat.NotFoundException;
import com.bingo.io.tomcat.WebXmlReader;
import com.bingo.io.tomcat.bio.MiniHttpServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.io.IOException;

/**
 * @author Bingo
 * @title: MiniNettyTomcatHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  18:49
 */
public class MiniNettyTomcatHandler extends ChannelInboundHandlerAdapter {
    WebXmlReader reader;

    public MiniNettyTomcatHandler(WebXmlReader reader) {
        this.reader = reader;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            System.out.println(msg);
            HttpRequest req = (HttpRequest) msg;

            // 转交给我们自己的request实现
            MiniNettyRequest request = new MiniNettyRequest(ctx, req);
            // 转交给我们自己的response实现
            MiniNettyResponse response = new MiniNettyResponse(ctx, req);
            // 实际业务处理
            String url = request.getUrl();
            System.out.println(url);

            String servletName = reader.getUrlMappings().get(url);
            try {
                if (servletName == null || "".equals(servletName)) {
                    throw new NotFoundException("no servlet name found by url " + url);
                }
                Class<?> clazz = reader.getServletMappings().get(servletName);
                if (clazz == null) {
                    throw new NotFoundException("no servlet class found by name " + servletName);
                }
                boolean isServlet = false;
                for (Class<?> inter : clazz.getInterfaces()) {
                    if (inter.getName().equals(MiniNettyHttpServlet.class.getName())) {
                        isServlet = true;
                        break;
                    }
                }

                MiniNettyHttpServlet servlet = (MiniNettyHttpServlet) clazz.newInstance();
                servlet.doService(request, response);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                try {
                    response.write("Sorry. Page Not Found!");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    request.close();
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
