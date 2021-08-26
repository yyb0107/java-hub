package com.bingo.curator.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: App
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/29  20:44
 */
public class App {

    static String CONNECT_STRING = "127.0.0.1:2181";//"192.168.1.110:2181,192.168.1.111:2181,192.168.1.112:2181";
    static CuratorFramework curatorFramework;

    public static void main(String[] args) throws Exception {

        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .connectionTimeoutMs(5000)
                .authorization("digest", "admin:admin".getBytes())
                .retryPolicy(new ExponentialBackoffRetry(1000, 1))
                .build();

        List<ACL> aclList = new ArrayList<>();
        aclList.add(new ACL(ZooDefs.Perms.READ|ZooDefs.Perms.WRITE|ZooDefs.Perms.ADMIN|ZooDefs.Perms.CREATE,new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin"))));

        curatorFramework.start();

        System.out.println(curatorFramework.getState());
        System.out.println(curatorFramework.getCurrentConfig());

        System.out.println("start api test……");
        showNodes();

        removeNode();

        createNode();
        curatorFramework.setACL().withACL(aclList).forPath("/yang");

        createTempNode(aclList);

        addChildListener();

        addListener();

        addListener();

        System.out.println("create node end…");
        TimeUnit.SECONDS.sleep(1000);

//        curatorFramework.close();
    }

    private static void addChildListener() {
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, "/yang", true);

        PathChildrenCacheListener listener = (curatorFramework, pathChildrenCacheEvent)->{
            System.out.println("监听到事件：--type:"+pathChildrenCacheEvent.getType()+"--data:"+pathChildrenCacheEvent.getData()+"--initData:"+pathChildrenCacheEvent.getInitialData());
        };
        childrenCache.getListenable().addListener(listener);
        try {
            childrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addListener(){
        NodeCache nodeCache = new NodeCache(curatorFramework, "/yang");

        NodeCacheListener listener = ()->{
            System.out.println("收到事件 nodeChanged");
        };

        nodeCache.getListenable().addListener(listener);
        try {
            nodeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTempNode(List<ACL> aclList) {
        try {
            String result = curatorFramework.create().withMode(CreateMode.EPHEMERAL).withACL(aclList).forPath("/yang/temp");
            System.out.println("tempNode:"+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String createNode(){
        String result = null;
        try {
            result = curatorFramework.create().creatingParentContainersIfNeeded().forPath("/yang");
            System.out.println("createNode :"+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void removeNode(){
        try {
            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/yang");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> showNodes(){
        List<String> result = null;
        try {
            result = curatorFramework.getChildren().forPath("/");
            System.out.println("showNodes "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
