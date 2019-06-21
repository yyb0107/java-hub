package reentrantlock;

import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: BlockQueueDemo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/21  22:48
 */
public class BlockQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockQueue<String> queue = new BlockQueue<>(3);
        String[] strs = new String[]{"Hello World", "Good Morning", "Good Afternoon", "Good Evening", "Good Night"};

        new Thread(() -> {
            String str = queue.take();
            System.out.println(String.format("Thread[%s] take %s success", Thread.currentThread().getId(), str));
        }).start();
        TimeUnit.SECONDS.sleep(3);
        new Thread(() -> {
            for (int i = 0; i < strs.length; i++) {
                boolean rs = queue.put(strs[i]);
                System.out.println(String.format("Thread[%s] put rs[%s] %s", Thread.currentThread().getId(), strs[i], rs));
            }
        }).start();
    }
}
