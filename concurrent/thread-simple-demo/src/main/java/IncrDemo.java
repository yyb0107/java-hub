import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: IncrDemo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/17  21:37
 */
public class IncrDemo {

    volatile static int x = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {

                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                x++;
            }).start();

        }

        System.out.println(x);
    }
}
