package com.bingo.rpc.server.zkregistry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @author Bingo
 * @title: ZkRegistry
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/30  20:21
 */
public class ZkRegistry {

    public static final String CONNECT_STR = "192.168.1.110:2181,192.168.1.111:2181,192.168.1.112:2181";

    static CuratorFramework curatorFramework;

    static {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STR).retryPolicy(new ExponentialBackoffRetry(1000, 1))
                .namespace("rpc")
                .connectionTimeoutMs(3000).build();
        curatorFramework.start();
    }

    /**
     * serviceName作为一个持久化节点，serviceAddress作为serviceName下一个临时自己点，这样，当程序出现异常退出后，zk将会自动删除该节点。
     * @param serviceName
     * @param serviceAddress
     */
    public static void registry(String serviceName,String serviceAddress){

        serviceName = "/"+serviceName;
        try {
            if(curatorFramework.checkExists().forPath(serviceName) == null){
                curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(serviceName);
            }

            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(serviceName+"/"+serviceAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String host(){
        String host = null;
        try {
            host = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }

    public static void main(String[] args) {
        System.out.println(host());
    }


}
