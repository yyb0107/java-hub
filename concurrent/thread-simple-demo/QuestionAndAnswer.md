
1. 请列出Happens-before的几种规则
    * 单线程的顺序执行原则
    * volatile规则，对volatile变量的修改happens-before volatile变量的查询
    * 线程start规则：一个线程被stat之前的操作happens-before线程启动之后的操作
    * 线程join规则：一个线程join之前的操作hanppens-before线程join之后的操作
    * synchronize规则：一个线程释放synchronize语句块之前的操作hanppens-before另一个线程或许synchronize语句块后的操作
    * 传递性规则：如果p1 hb p2 ,p2 hp p3 那么 p1 hb p3
    
    
2. volatile 能使得一个非原子操作变成原子操作吗？为什么？
    * volatile不能是一个非原子操作编程原子操作。volatile只是将变量更新之后的值在其他地方获取是可见的，但不并没有阻止变量可能同时更新，如果有多个线程同时更新volatile变量，原子性无法保障
        
3. 哪些场景适合使用Volatile
    * 多线程环境下需要状态变量来进行不同的操作时。
    
4. 如果对一个数组修饰volatile，是否能够保证数组元素的修改对其他线程的可见？为什么？
    * 可见，因为volatile是通过jmm来触发一次内存屏障，将当前线程中缓存的值更新后强制push到主内存中，并通知其他线程重新从主内存获取最新的数据。
    (ps: 一开始我也觉得是不可见的，但用程序告诉我是可见的)
   
```java
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
```

 