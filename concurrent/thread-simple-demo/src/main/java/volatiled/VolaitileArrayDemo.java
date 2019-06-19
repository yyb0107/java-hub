package volatiled;

import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: VolaitileArrayDemo
 * @projectName java-hub
 */
public class VolaitileArrayDemo {

    static volatile Integer[] array = new Integer[]{1, 2, 3, 4, 5};

    public static void main(String[] args) throws InterruptedException {


        //t1
        Thread t1 = new Thread(() -> {
            while (array[1] < 10) {

            }
        });
        t1.start();

        TimeUnit.SECONDS.sleep(2);

        //如果是在线程t2更新array[1]的值，t1不可见
        Thread t2 = new Thread(() -> {
            array[1] = 10;
        });
        t2.start();

        //在主线程中更新array[1]的值，t1不可见
//        array[1] = 10;
    }

}
