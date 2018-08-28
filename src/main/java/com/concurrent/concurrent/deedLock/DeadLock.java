package com.concurrent.concurrent.deedLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 【DeadLock】
 * 一个简单的死锁类
 * 当DeadLock类的对象flag==1时（td1），先锁定o1,睡眠500毫秒
 * 而td1在睡眠的时候另一个flag==0的对象（td2）线程启动，先锁定o2,睡眠500毫秒
 * td1睡眠结束后需要锁定o2才能继续执行，而此时o2已被td2锁定；
 * td2睡眠结束后需要锁定o1才能继续执行，而此时o1已被td1锁定；
 * td1、td2相互等待，都需要得到对方锁定的资源才能继续执行，从而死锁。
 *
 *
 * 线程的死锁条件:
 1，互斥条件 ： 在某段时间只能有一个线程占用资源，如果有其他线程请求占用则必须等待，直到释放资源；
 2，请求和保持条件： 已经占有至少一个资源，又提出请求其他资源，该资源已经被其他线程占用，请求线程阻塞，但是又对已占用的资源不放
 3，不剥夺条件： 对已占用的资源不会被剥夺，只能自己释放
 4，环路等待条件：发生死锁的时候，一定存在一个线程，他是一个死锁的环形链；
 *
 *
 * 线程死锁避免方法：
 1，线程按照一定顺序进行加锁
 2，加锁时限
 3，死锁检测
 */

@Slf4j
public class DeadLock implements Runnable {
    public int flag = 1;
    //静态对象是类的所有对象共享的
    private static Object o1 = new Object(), o2 = new Object();

    @Override
    public void run() {
        log.info("flag:{}", flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    log.info("1");
                }
            }
        }
        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    log.info("0");
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLock td1 = new DeadLock();
        DeadLock td2 = new DeadLock();
        td1.flag = 1;
        td2.flag = 0;
        //td1,td2都处于可执行状态，但JVM线程调度先执行哪个线程是不确定的。
        //td2的run()可能在td1的run()之前运行
        new Thread(td1).start();
        new Thread(td2).start();
    }
}
