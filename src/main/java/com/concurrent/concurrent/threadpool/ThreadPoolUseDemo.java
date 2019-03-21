package com.concurrent.concurrent.threadpool;


import java.util.concurrent.*;

/**
 * 线程池的使用
 * ref:https://www.jianshu.com/p/210eab345423
 */
public class ThreadPoolUseDemo implements Runnable {
    protected int countDown = 10; //Default
    private static int taskCount = 0;
    private final int id = taskCount++;

    public ThreadPoolUseDemo() {
    }

    public ThreadPoolUseDemo(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + Thread.currentThread().getName() + "--(" +
                (countDown > 0 ? countDown : " ThreadPoolUseDemo!") + ") ";
    }

    public void run() {
        while (countDown-- > 0) {
            System.out.println(status());
            Thread.yield();
        }

    }
}

/**
 * 使用FixedThreadPool执行任务
 * SynchronousQueue：这个队列接收到任务的时候，会直接提交给线程处理，而不保留它，如果所有线程都在工作怎么办？
 * 那就新建一个线程来处理这个任务！所以为了保证不出现<线程数达到了maximumPoolSize而不能新建线程>的错误，
 * 使用这个类型队列的时候，maximumPoolSize一般指定成Integer.MAX_VALUE，即无限大
 */
class FixedThreadPool {
    public static void main(String[] args) {
        //三个线程来执行五个任务
        ThreadPoolExecutor exec = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(30));
//        ThreadPoolExecutor exec = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
//        ExecutorService exec = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            exec.execute(new ThreadPoolUseDemo());
//            exec.submit(new ThreadPoolUseDemo());
        }
        exec.shutdown();
    }
}

/**
 * 使用CachedThreadPool执行任务,核心线程数为0,线程总数无限大,有空闲线程则复用空闲线程，若无空闲线程则新建线程
 * SynchronousQueue：这个队列接收到任务的时候，会直接提交给线程处理，而不保留它，
 * 如果所有线程都在工作怎么办？那就新建一个线程来处理这个任务！
 * 所以为了保证不出现<线程数达到了maximumPoolSize而不能新建线程>的错误，使用这个类型队列的时候，
 * maximumPoolSize一般指定成Integer.MAX_VALUE，即无限大
 */
class CachedThreadPool {
    public static void main(String[] args) {
//        ThreadPoolExecutor exec = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
//            exec.execute(new ThreadPoolUseDemo());
            exec.submit(new ThreadPoolUseDemo());
        }
        exec.shutdown();
    }
}

/**
 * 使用singleThreadExecutor执行任务
 * 只有一个线程执行
 */
class SingleThreadExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
//        ExecutorService exec = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 3; i++) {
            exec.execute(new ThreadPoolUseDemo());
//            exec.submit(new ThreadPoolUseDemo());

        }
    }
}