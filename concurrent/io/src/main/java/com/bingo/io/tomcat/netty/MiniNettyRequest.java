package com.bingo.io.tomcat.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bingo
 * @title: MiniNettyRequest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  16:59
 */
public class MiniNettyRequest {
    private ChannelHandlerContext context;
    private HttpRequest req;

    public MiniNettyRequest(ChannelHandlerContext ctx, HttpRequest req) {
        this.context = ctx;
        this.req = req;
    }


    /**
     * 获取请求的url-pattern
     * 目前只支持Get请求
     *
     * @return
     */
    public String getUrl() {
        String urlPattern = null;
        if (isGet()) {
            String url = req.uri();
            ///first.do?uname=bingo&password=123243 HTTP/1.1
            url = url.substring(0,url.length()-9);//去掉 HTTP/1.1
            int charIndex = url.indexOf("?");
            if (charIndex != -1) {
                urlPattern = url.substring(0, charIndex);
                String args = url.substring(charIndex+1);
            }
            return urlPattern;
        }
        return null;
    }

    public String getParameter(String key) {
        QueryStringDecoder query = new QueryStringDecoder(req.uri());
        List<String> strs = query.parameters().get(key);
        return strs.isEmpty()?null:strs.get(0);
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder query = new QueryStringDecoder(req.uri());
        return query.parameters();
    }


    private boolean isGet() {
        return req.method() == HttpMethod.GET?true:false;
    }

    public void close() throws IOException {
        context.close();
    }

}
