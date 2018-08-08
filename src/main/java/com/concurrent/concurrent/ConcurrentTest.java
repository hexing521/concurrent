package com.concurrent.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 使用代码模仿客户端的并发请求demo
 * 【Semaphore】信号量，Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 */
public class ConcurrentTest {

    //模拟客户端总数
    public static int ClientTotal = 5000;
    //模拟线程总数
    public static int ThreadTotal = 200;
    //计数器
    public static int total = 0;


    public static void main(String[] args) throws Exception {
        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //todo 信号量 控制并行度 允许最大并行的线程总数是ThreadTotal
        Semaphore semaphore = new Semaphore(ThreadTotal);
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(total);
        //模拟5000个客户端请求
        for (int i = 0; i < ClientTotal; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        /**
                         * todo 获取许可 判断当前线程能否执行，如果并发量达到ThreadTotal个则add方法就会被阻塞掉
                         * 用来获取一个许可，若无许可能够获得，则会一直等待，直到获得许可。
                         */
                        semaphore.acquire();
                        add();
                        /**
                         * todo 释放当前线程的许可，让其它线程获取许可证
                         * 用来释放许可。注意，在释放许可之前，必须先获获得许可。
                         */
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            countDownLatch.countDown();//将count值减1
        }

        countDownLatch.await();//todo 它会等待直到count值为0才继续执行
        executorService.shutdown();//关闭线程池
        System.out.println("total result is:" + total);


    }

    public static void add() {
        total++;
    }

}
