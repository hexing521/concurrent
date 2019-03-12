package com.concurrent.concurrent.thread;

/**
 * 每个线程都有个boolean类型的中断状态。
 * 当使用Thread的interrupt()方法时，线程的中断状态会被设置为true。
 * <p>
 * 对线程调用interrupt()方法，不会真正中断正在运行的线程，只是发出一个请求，
 * 由线程在合适时候结束自己。
 */
public class InterruptedExample {

    public void start() {
        MyThread myThread = new MyThread();
        myThread.start();

        try {
            Thread.sleep(3000);
            myThread.cancel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class MyThread extends Thread {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("test");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("interrupt");
                    //抛出InterruptedException后中断标志被清除，标准做法是再次调用interrupt恢复中断
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("stop");
        }

        public void cancel() {
            interrupt();
        }
    }

    public static void main(String[] args) throws Exception {
        InterruptedExample interruptedExample = new InterruptedExample();
        interruptedExample.start();
    }
}
