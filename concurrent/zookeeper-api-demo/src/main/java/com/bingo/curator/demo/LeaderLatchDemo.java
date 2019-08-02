package com.bingo.curator.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.*;
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
public class LeaderLatchDemo implements Closeable,LeaderLatchListener {

    String name;

    LeaderLatch leaderLatch;

    static String CONNECT_STRING = "192.168.1.110:2181,192.168.1.111:2181,192.168.1.112:2181";

    static {

    }

    public LeaderLatchDemo(String name) {
        this.name = name;
    }


    public void setLeaderLatch(LeaderLatch leaderLatch) {
        this.leaderLatch = leaderLatch;
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
                LeaderLatchDemo demo = new LeaderLatchDemo("Thread-0" + tmp);
                CuratorFramework curatorFramework = curatorFramework();
                curatorFramework.start();
                System.out.println(demo.name+" curator 启动完成。");
                LeaderLatch leaderLatch = new LeaderLatch(curatorFramework, "/LeaderLatch");
                leaderLatch.addListener(demo);
                demo.setLeaderLatch(leaderLatch);

                try {
                    leaderLatch.start();
//                    Participant participant = leaderLatch.getLeader();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(demo.name+" 执行结束。");
            }).start();

        }
        System.in.read();
    }


    @Override
    public void close() throws IOException {
        leaderLatch.close();

    }

    @Override
    public void isLeader() {
        System.out.println(name + "是leader了");
        try {
            TimeUnit.SECONDS.sleep(10);
            this.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void notLeader() {
        System.out.println(name+" 不是leader了");
    }
}
