package reentrantlock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Bingo
 * @title: ReentrantReadWriteLockDemo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/20  23:09
 */
public class ReentrantReadWriteLockDemo {

    /**
     * ReentrantReadWriteLock 读写重入锁（互斥锁，也是共享锁）
     * 读-读  共享
     * 读-写  互斥
     * 写-读  互斥
     * 写-写  互斥
     */
    ReentrantReadWriteLock readWriteLock;

    Lock readLock;
    Lock writeLock;

    public ReentrantReadWriteLockDemo(ReentrantReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    final static Map<String, String> container = new HashMap<String, String>();

    public String get(String key) {
        String value = null;
        try {
            readLock.lock();
            TimeUnit.SECONDS.sleep(5);
            String rs = container.get(key);
            TimeUnit.SECONDS.sleep(5);
            System.out.println("读执行完毕，读到的值为： " + rs);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
        return null;
    }

    public void set(String key, String value) {
        try {
            writeLock.lock();
            container.put(key, value);
            System.out.println("写执行完毕,container size : " + size() + " 当前key=" + key);
            TimeUnit.SECONDS.sleep(5);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public int size() {
        return container.size();
    }

    public static void main(String[] args) {

        Random random = new Random();
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo(lock);
                String key = random.nextInt(1000) + "";
                System.out.println("key " + key);
                demo.set(key, random.nextInt(100) + "");
                try {
                    //这个时间的等待是并行的
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo(lock);
                String rs = demo.get(random.nextInt(1000) + "");

            }).start();
        }


    }
}
