package com.bingo.io.tomcat.bio;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bingo
 * @title: MiniNettyRequest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  16:59
 */
public class MiniRequest {
    private int bufferSize = 1024;
    private Map<String, String> metaData;
    private InputStream inputStream;
    private Map<String, String> parameters;
    volatile boolean isGet = false;
    private String httpVersion;


    public MiniRequest(InputStream inputStream) throws IOException {
        metaData = new HashMap<>();
        parameters = new HashMap<>();
        byte[] buf = new byte[bufferSize];
        int len = -1;
        StringBuffer sb = new StringBuffer();
        while ((len = inputStream.read(buf)) != -1 || len > 0) {
            String line = new String(buf, 0, len);
            sb.append(line);
            if (line.startsWith("GET")) {
                isGet = true;
            }
            if (len < bufferSize || !isGet) {
                break;
            }
        }
        String[] strs = sb.toString().split("\n");
        for (String str : strs) {
            int splitIndex = str.indexOf(" ");
            if (splitIndex == -1) {
                continue;
            }
            metaData.put(str.substring(0, splitIndex), str.substring(splitIndex + 1));
        }

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
            String url = metaData.get("GET");
            ///first.do?uname=bingo&password=123243 HTTP/1.1
            url = url.substring(0,url.length()-9);//去掉 HTTP/1.1
            int charIndex = url.indexOf("?");
            if (charIndex != -1) {
                urlPattern = url.substring(0, charIndex);
                String args = url.substring(charIndex+1);
                //如果有?号，开始初始化parameter
                String[] ps = args.split("&");
                for (String p : ps) {
                    parameters.put(p.substring(0, p.indexOf("=")), p.substring(p.indexOf("=") + 1));
                }
            }
            return urlPattern;
        }
        return null;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }


    private boolean isGet() {
        return isGet;
    }

    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }

}
