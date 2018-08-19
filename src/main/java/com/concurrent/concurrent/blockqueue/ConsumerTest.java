package com.concurrent.concurrent.blockqueue;

import java.util.concurrent.BlockingQueue;

public class ConsumerTest implements Runnable {
    BlockingQueue<String> queue;

    public ConsumerTest(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            System.out.println("I have take a E:" + Thread.currentThread().getName());
            String temp = queue.take();//如果队列为空，会阻塞当前线程
            System.out.println(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
