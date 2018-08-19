package com.concurrent.concurrent.blockqueue;

import java.util.concurrent.BlockingQueue;

public class ProducerTest implements Runnable {
    BlockingQueue<String> queue;

    public ProducerTest(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            String temp = "A Product, 生产线程：" + Thread.currentThread().getName();
            System.out.println("I have made a product:" + Thread.currentThread().getName());
            queue.put(temp);//如果队列是满的话，会阻塞当前线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

