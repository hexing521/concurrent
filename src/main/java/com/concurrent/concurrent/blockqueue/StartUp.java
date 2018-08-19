package com.concurrent.concurrent.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {
    private final BlockingQueue queue;

    Producer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
//            while (true) {
                for (int i=0;i<=1200;i++){
                    System.out.println("==============producer data");
//                    System.out.println("---------------------queue size:"+queue.size());
                    queue.put(produce());
                }
//            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    Object produce() {
        return "";
    }
}

class Consumer implements Runnable {
    private final BlockingQueue queue;

    Consumer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                System.out.println("consumer data==============");
                System.out.println("---------------------queue size:"+queue.size());
                consume(queue.take());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    void consume(Object x) {

    }
}


public class StartUp {

    public static void main(String[] args) {
//        BlockingQueue q = new SomeQueueImplementation();
        BlockingQueue q = new ArrayBlockingQueue(1000);
        ProducerTest p = new ProducerTest(q);
        ConsumerTest c1 = new ConsumerTest(q);
        ConsumerTest c2 = new ConsumerTest(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }
}
