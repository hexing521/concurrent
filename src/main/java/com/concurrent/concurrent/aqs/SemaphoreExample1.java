package com.concurrent.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *【Semaphore】
 * 信号量  对线程的并发数的控制
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
