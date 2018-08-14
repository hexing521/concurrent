package com.concurrent.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 【CyclicBarrier】
 *  阻塞等待多个线程一起执行
 *
 * CyclicBarrier可以重用，可以用于多线程计算数据，最后合并计算结果的应用场景（适用于某些协议或规则）。
 *
 * CountDownLatch和CyclicBarrier区别：
 *（1）CountDownLatch计数器只能使用一次，CyclicBarrier计数器可以使用reset方法重置。
 *（2）CountDownLatch，描述的是一个或N个线程等待其他线程的关系（等待完成某项操作，强调完成某项操作），
 * CyclicBarrier，各个线程之间内部相互等待的关系。（所有的线程必须全部到达栅栏位置，才继续执行，强调线程）。
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
