package com.bingo.rpc.client.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.*;

/**
 * @author Bingo
 * @title: ZKDiscovery
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/30  21:19
 */
public class ZKDiscovery {
    public static final String CONNECT_STR = "192.168.1.110:2181,192.168.1.111:2181,192.168.1.112:2181";

    static CuratorFramework curatorFramework;

    public Map<String,List<String>> repository = new HashMap<>();

    static {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STR).retryPolicy(new ExponentialBackoffRetry(1000, 1))
                .namespace("rpc")
                .connectionTimeoutMs(3000).build();
        curatorFramework.start();
    }

    public String getServiceAddress(String serviceName){
        serviceName = "/"+serviceName;
        List<String> address = new ArrayList<String>();
        if(!repository.containsKey(serviceName) || repository.get(serviceName).isEmpty()){
            try {
                if(curatorFramework.checkExists().forPath(serviceName) !=null){
                    address = curatorFramework.getChildren().forPath(serviceName);
                    System.out.println(address);
                    repository.putIfAbsent(serviceName, address);
                    registryListener(serviceName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            address = repository.get(serviceName);
        }

        String addressAndPort = balanceAddress(address);
        System.out.println(addressAndPort);
        return addressAndPort;
    }

    private String balanceAddress(List<String> address) {
        int size = address.size();
        Random random =new Random();
        return address.get(random.nextInt()%size);
    }

    private void registryListener(String serviceName) {
        PathChildrenCache cache = new PathChildrenCache(curatorFramework, serviceName, true);

        cache.getListenable().addListener((curatorFramework,pathChildrenCacheEvent)->{
            List<String> address = curatorFramework.getChildren().forPath(serviceName);
            repository.put(serviceName, address);
        });
        try {
            cache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
