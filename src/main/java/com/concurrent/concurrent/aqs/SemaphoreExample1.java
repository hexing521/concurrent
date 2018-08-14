package com.concurrent.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *【Semaphore】
 * 信号量  对线程的并发数的控制
 *Semaphore常用于仅能提供有限访问的资源的并发数，比如数据库的连接数
 *
 * Semaphore常用于仅能提供有限访问的资源
 * 1、可以一次获取多个许可semaphore.acquire(n)，也可以一次是否多个许可semaphore.release(n)
 * 2、可以尝试获取许可（单个或多个）tryAcquire()，并且可以设置尝试获取的超时时间
 */
@Slf4j
public class SemaphoreExample1 {

    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    semaphore.acquire(); //TODO 获取一个许可
                    test(threadNum);
                    semaphore.release(); //TODO 释放一个许可

//                    semaphore.acquire(3); //TODO 获取多个许可
//                    test(threadNum);
//                    semaphore.release(3); //TODO 释放多个许可
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        log.info("{}", threadNum);
        Thread.sleep(1000);
    }
}
