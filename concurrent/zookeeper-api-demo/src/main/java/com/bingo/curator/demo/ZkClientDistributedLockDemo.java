package com.bingo.curator.demo;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: ZkClientDistributedLockDemo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/8/7  21:00
 */
public class ZkClientDistributedLockDemo {

    static String CONNECT_STRING = "192.168.1.110:2181,192.168.1.111:2181,192.168.1.112:2181";


    public static void main(String[] args) throws IOException {
        ZkClient zkClient = new ZkClient(CONNECT_STRING, 50000);


        for(int i=0;i<10;i++){
            new Thread(()->{
                ZkClientDistributedLock lock = new ZkClientDistributedLock("/lock", zkClient);
                lock.lock();

                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock.unlock();
            },"T-"+i).start();
        }
        System.in.read();


    }
}
