package com.concurrent.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 【CyclicBarrier】
 *  阻塞等待多个线程一起执行
 */
@Slf4j
public class CyclicBarrierExample1 {

    /**
     * 声明一个CyclicBarrier  并设置阻塞等待五个线程之后再执行
     */
    private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        executor.shutdown();
    }

    private static void race(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        barrier.await();//TODO 等待阻塞线程
        log.info("{} continue", threadNum);
    }
}
