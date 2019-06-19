package volatiled;

import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: VolaitileDemo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/19  0:18
 */
public class VolaitileDemo {

    static boolean isFinish = false;

    static  Object obj = null;

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            int x = 0;
            while(!isFinish){
                x++;
//                System.out.println(1);
            }
        }).start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(()->{
            isFinish = true;
//            obj = new Object();
        }).start();

//        isFinish = true;
    }


}
