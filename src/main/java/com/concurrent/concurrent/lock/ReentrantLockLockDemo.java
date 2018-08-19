package com.concurrent.concurrent.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 【ReentrantLock锁机制】
 *  原理：加锁 内部的计数器+1  释放锁 内部的计数器为0才释放锁
 *  ====都是在用户端解决加锁的问题从而避免内核态的阻塞等待===
 *
 *  ReentranLock独有的功能:
 *  1,可指定公平锁或者是非公平锁
 *  2,提供一个condition类，可以分组唤醒需要唤醒的线程
 *  3,提供能够终端等待锁的机制 lock.lockInterruptibly()
 *
 *
 */
@Slf4j
public class ReentrantLockLockDemo {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static int count = 0;

    /**
     * 默认使用NonfairSync（非公平锁）
     */
    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();//todo 释放锁
        }
    }
}
