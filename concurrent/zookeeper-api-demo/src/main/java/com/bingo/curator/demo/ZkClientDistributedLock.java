package com.bingo.curator.demo;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Bingo
 * @title: ZkClientDistributedLock
 * @projectName java-hub
 * @description: TODO
 * @date 2019/8/1  22:09
 */
public class ZkClientDistributedLock implements Lock {

    private String nodeRoot = "/lock";

    private ZkClient zkClient;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private String currentNode;

    public ZkClientDistributedLock(String nodeRoot, ZkClient zkClient) {
        this.nodeRoot = nodeRoot;
        this.zkClient = zkClient;
    }

    /**
     * 获取锁
     */
    @Override
    public void lock() {
        //如果创建节点失败
        int lockCount = 10;
        while (lockCount-- > 0 && !zkClient.exists(nodeRoot)) {
            try {
                zkClient.createPersistent(nodeRoot);
            } catch (Exception e) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        currentNode = zkClient.createEphemeralSequential(String.format("%s/%s", nodeRoot, "lock"), null);
        List<String> children = zkClient.getChildren(nodeRoot);
        String previous = null;
        boolean hasWaited = false;
        for (int i = 0; i < children.size(); i++) {
            if (String.format("%s/%s",nodeRoot,children.get(i)).equals(currentNode)) {
                if (i == 0 || hasWaited) {
                    //表示当前节点是第一个，可以获得锁
                    System.out.println(String.format("%s-%s", Thread.currentThread().getName(), "成功获得锁"));
                    break;
                } else {
                    //表示当前节点不是第一个，那就对前一个节点进行监听,并进行阻塞
                    zkClient.subscribeDataChanges(String.format("%s/%s", nodeRoot, previous), new IZkDataListener() {
                        @Override
                        public void handleDataChange(String s, Object o) throws Exception {
                            System.out.println("nothing to do.");
                        }

                        @Override
                        public void handleDataDeleted(String s) throws Exception {
                            countDownLatch.countDown();
                        }
                    });

                    try {
                        System.out.println(String.format("%s-%s", Thread.currentThread().getName(), "开始等待"));
                        countDownLatch.await();
                        children = zkClient.getChildren(nodeRoot);
                        i = -1; //因为后面还有i++的操作，所以这里设为-1
                        hasWaited = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                //当前节点不是首个节点，则先记录这个节点，当找到当前节点时，可以通过previous确定它前一个节点。
                previous = children.get(i);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if (zkClient.delete(currentNode)) {
            System.out.println(String.format("%s-%s", Thread.currentThread().getName(), "成功释放锁"));
        }

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
