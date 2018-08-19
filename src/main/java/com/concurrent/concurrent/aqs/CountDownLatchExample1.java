package com.concurrent.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 【countDownLatch】
 * 主要是来做线程同步的组件
 *
 * 具有原子性减一操作的不可重置计数器
 * 应用场景：并行计算时，需要等待全部线程均执行完成，统计执行结果，才继续后面的逻辑
 *
 * CountDownLatch:
 * 两个方法：countDown()  await()
 * 创建 CountDownLatch对象时，需要指定一个计数的数字
 * 调用countDown()计数-1，当countDown后为0时，调用await（）方法后面的代码才会继续执行。
 * 可以在await（）中指定时间，表示过了这个时间，不管线程是否计数完毕都会往下继续执行。
 */
@Slf4j
public class CountDownLatchExample1 {

    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        //TODO 初始化CountDownLatch 并且设置CountDownLatch的初始值为threadCount
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
                    countDownLatch.countDown();//todo countDownLatch的值依次减1
                }
            });
        }
        countDownLatch.await();//TODO 阻塞等待countDownLatch的值减为0的时候才会执行下面的代码
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        log.info("{}", threadNum);
        Thread.sleep(100);
    }
}
