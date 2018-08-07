package com.concurrent.concurrent.concurrentContainor;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * 【并发容器】
 *
 * CopyOnWriteArrayList的读操作：
 * 1、如果写操作未完成，那么直接读取原数组的数据；
 * 2、如果写操作完成，但是引用还未指向新数组，那么也是读取原数组数据；
 * 3、如果写操作完成，并且引用已经指向了新的数组，那么直接从新数组中读取数据。
 * 可见，CopyOnWriteArrayList的读操作是可以不用加锁的。
 *
 * CopyOnWriteArrayList的特性：
 * 1、读写分离，读和写分开
 * 2、最终一致性
 * 3、使用另外开辟空间的思路，来解决并发冲突
 */
@Slf4j
public class CopyOnWriteArrayListExample {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    private static List<Integer> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("size:{}", list.size());
    }

    private static void update(int i) {
        list.add(i);
    }
}
