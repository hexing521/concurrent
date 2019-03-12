package com.concurrent.concurrent.thread;

import java.util.concurrent.*;

public class InterruptByFuture {

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<?> task = es.submit(new MyThread());

        try {
            //限定时间获取结果
            task.get(5, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            //超时触发线程中止
            System.out.println("thread over time");
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw e;
        } finally {
            boolean mayInterruptIfRunning = true;
            task.cancel(mayInterruptIfRunning);
        }
    }

    private static class MyThread extends Thread {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("count");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("interrupt");
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("thread stop");
        }

        public void cancel() {
            interrupt();
        }
    }
}
