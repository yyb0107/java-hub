package com.bingo.curator.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: LeaderSelectorDemo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/31  23:29
 */
public class LeaderSelectorDemo extends LeaderSelectorListenerAdapter implements Closeable {

    String name;

    LeaderSelector selector;

    static String CONNECT_STRING = "192.168.1.110:2181,192.168.1.111:2181,192.168.1.112:2181";

    static {

    }

    public LeaderSelectorDemo(String name) {
        this.name = name;
    }


    public void setSelector(LeaderSelector selector) {
        this.selector = selector;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CuratorFramework curatorFramework() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 1))
                .build();
        return curatorFramework;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        for (int i = 0; i < 10; i++) {
            int tmp = i;
            new Thread(() -> {
                LeaderSelectorDemo demo = new LeaderSelectorDemo("Thread-0" + tmp);
                CuratorFramework curatorFramework = curatorFramework();
                curatorFramework.start();
                System.out.println(demo.name+" curator 启动完成。");
                LeaderSelector selector = new LeaderSelector(curatorFramework, "/leaderSelector", demo);
                demo.setSelector(selector);
                selector.start();//这个方法是异步的
                System.out.println(demo.name+" 执行结束。");
            }).start();

        }
        System.in.read();
    }


    @Override
    public void close() throws IOException {
        selector.close();

    }

    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
        System.out.println(name + "成功竞选为leader了");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
