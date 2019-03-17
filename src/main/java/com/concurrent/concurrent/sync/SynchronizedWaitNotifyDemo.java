package com.concurrent.concurrent.sync;

import java.util.concurrent.TimeUnit;

/**
 * Synchronied关键字修改的方法或者代码块，保证了同一时间只会有一个线程进入到临界区(互斥性)，
 * 同时它还保证了共享变量在线程间的可见性;
 * <p>
 * Java中的每个对象都可以作为锁。
 * 1,普通的同步方法，锁是当前对象
 * 2,静态同步方法，锁是当前类的class对象
 * 3,同步代码块，锁是括号中的对象
 */
public class SynchronizedWaitNotifyDemo {

    static boolean flag = true;
    static Object lock = new Object();

    /**
     * 线程A在获取锁后调用了对象lock的wait方法进入了等待状态，线程B调用对象lock的notifyAll()方法，
     * 线程A收到通知后从wait方法处返回继续执行，线程B对共享变量flag的修改对线程A来说是可见的。
     * <p>
     * 整个执行过程:
     * 1,使用wait()、notify()和notifyAll()时需要先对调用对象加锁，调用wait()方法后会释放锁。
     * 2,调用wait()方法之后，线程状态由RUNNING变为WAITING，并将当前线程放置到对象的等待队列中。
     * 3,notify()或notifyAll()方法调用后，等待线程不会立刻从wait()中返回，需要等该线程释放锁之后，才有机会获取锁之后从wait()返回。
     * 4,notify()方法将等待队列中的一个等待线程从等待队列中移动到同步队列中；notifyAll()方法则是把等待队列中的所有线程都移动到同步队列中；
     * 被移动的线程状态从WAITING变为BLOCKED。
     * 5,从wait()方法返回的前提是，改线程获得了调用对象的锁。
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread A = new Thread(new Wait(), "wait thread");
        A.start();
        TimeUnit.SECONDS.sleep(2);
        Thread B = new Thread(new Notify(), "notify thread");
        B.start();
    }

    static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true");
                        lock.wait();
                    } catch (InterruptedException e) {

                    }
                }
                System.out.println(Thread.currentThread() + " flag is false");
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName());
                flag = false;
                lock.notifyAll();
                try {
                    TimeUnit.SECONDS.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
