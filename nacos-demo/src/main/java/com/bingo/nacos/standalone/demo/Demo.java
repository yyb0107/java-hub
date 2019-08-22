package com.bingo.nacos.standalone.demo;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.NacosConfigService;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author Bingo
 * @title: Demo
 * @projectName java-hub
 * @description: 主要是利用NacosConfigService从服务器获取配置信息，并且在在configService上进行配置信息的监听
 * @date 2019/8/22  22:42
 */
public class Demo {
    static String dataId="mydatabase";
    static String groupId="NACOS:TEST";
    static String serverHost="192.168.1.110:8848";
    static ConfigService configService;

    static{
        Properties pro = new Properties();
        pro.put(PropertyKeyConst.SERVER_ADDR,serverHost);
        try {
            configService = new NacosConfigService(pro);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        getConfig();

//        publishConfig();

        delConfig();
    }

    public static void getConfig(){
        try {

            String configInfo = configService.getConfig(dataId, groupId, 5000);
            System.out.println(configInfo);

            configService.addListener(dataId, groupId, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String s) {
                    System.out.println("receiver config info : "+s);

                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
        try {
            //阻塞程序，好看到监听的效果
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void publishConfig(){
        try {
            //这里的newString将会覆盖原有内容
            boolean result = configService.publishConfig(dataId, groupId, "newString");
            System.out.println(result);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public static void delConfig(){
        try {
            boolean res = configService.removeConfig(dataId, groupId);
            System.out.println(res);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
