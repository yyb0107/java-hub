package reentrantlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Bingo
 * @title: BlockQueue
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/21  22:26
 */
public class BlockQueue<T> {
    private final List<T> blockQueue;
    private int initialCapacity;
    private volatile int remaining;


    public BlockQueue(int initialCapacity) {
        blockQueue = new ArrayList<T>(initialCapacity);
        this.initialCapacity = initialCapacity;

        this.remaining = initialCapacity;
    }

    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    /**
     * 当队列为空时，请求take会被阻塞，直到队列不为空
     * @return
     */
    public T take() {
        try {
            lock.lock();
            if (blockQueue.isEmpty()) {
                condition.await();
            }
            T result = blockQueue.get(0);
            blockQueue.remove(result);
            remaining++;
            condition.signalAll();
            return result;
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 当队列满了以后，存储元素的线程需要备阻塞直到队列可以添加数据
     *
     * @param obj
     * @return
     */
    public boolean put(T obj) {
        try {
            lock.lock();
            boolean rs = false;
            if (remaining > 0) {
                rs = blockQueue.add(obj);
                remaining--;
                condition.signal();
            } else {
                condition.await();
            }
            return rs;
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return false;
    }
}
