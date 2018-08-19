package com.concurrent.concurrent.blockqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自己实现blockqueue
 * @param <T>
 */
public class MyBlockingQueue<T> {

    private final T[] items;

    /**
     * Main lock guarding all access
     */
    final ReentrantLock lock = new ReentrantLock();

    /**
     * Condition for waiting takes
     */
    private final Condition notEmpty = lock.newCondition();

    /**
     * Condition for waiting puts
     */
    private final Condition notFull = lock.newCondition();

    private int takeIndex, putIndex, count;

    MyBlockingQueue(int max) {
        this.items = (T[]) new Object[max];
    }

    public void put(T t) {
        lock.lock();
        try {
            //如果数据组的里对象 = 设定的数据那么说明满了,要等到被消费一个时才能重新放
            if (count == getLength()) {
                System.out.println("生产速度太快,爆仓,等待消费中……");
                notFull.await();
            }
            items[putIndex] = t;
            //如果放到最后一个位置了,那么,又得从第0个重新开始放
            if (++putIndex == getLength()) {
                putIndex = 0;
            }
            ++count;
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        lock.lock();
        T t = null;
        try {
            //如果数据组没内存,那么等待
            if (count == 0) {
                System.out.println("生产速度太慢了,员工等待中……");
                notEmpty.await();
            }
            t = items[takeIndex];
            //消除引用,避免内存泄露
            items[takeIndex] = null;
            //如果取到最后一个位置了,就从第0个开始取
            if (++takeIndex == getLength()) {
                takeIndex = 0;
            }
            --count;
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    private int getLength() {
        return items.length;
    }
}
