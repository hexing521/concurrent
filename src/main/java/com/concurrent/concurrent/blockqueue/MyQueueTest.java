package com.concurrent.concurrent.blockqueue;

import java.util.concurrent.TimeUnit;

/**
 * 自定义blockqueue测试
 */
public class MyQueueTest {

    public static void main(String[] args) {
        System.out.println("===========main thread start............");
        final MyBlockingQueue<String> queue = new MyBlockingQueue<String>(6);
        //todo:生产
        Thread put = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    queue.put("product:" + i);
                    System.out.println("生产了：" + "product" + i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //todo:消费
        Thread take = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    System.out.println("消费了：" + queue.take());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        put.start();
        take.start();
        try {
            put.join();
            take.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("===========main thread stop............");
    }
}
