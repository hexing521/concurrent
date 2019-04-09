package com.concurrent.concurrent.threadpool;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class MyRunnable implements Runnable {
    private String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " is running.");
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * java线程池的拒绝策略的实现:
 * 1,ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
 * 2,ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
 * 3,ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
 * 4,ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
 */
public class ThreadPoolRejectedDemo {
    private static int THREADS_SIZE = 1;
    private static int CAPACITY = 1;

    /**
     * abortPolicy:当线程的个数达到线程池的maximumPoolSize最大值时，如果等待队列已满则AbortPolicy
     * 将会丢弃新任务并抛出RejectedExecutionException异常。
     */
    public static void abortPolicyDemo() {

        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"抛出异常"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        try {
            // 新建10个任务，并将它们添加到线程池中。
            for (int i = 0; i < 10; i++) {
                Runnable myrun = new MyRunnable("task-" + i);
                pool.execute(myrun);
            }
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            pool.shutdown();
        }
    }

    /**
     * dicardPolicy和abortPolicy不同之处在于，dicardPolicy拒绝策略也会丢弃任务，但并不会抛出异常
     */
    public static void discardPolicyDemo() {

        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"丢弃"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        // 新建10个任务，并将它们添加到线程池中。
        for (int i = 0; i < 10; i++) {
            Runnable myrun = new MyRunnable("task-" + i);
            pool.execute(myrun);
        }
        // 关闭线程池
        pool.shutdown();

    }

    /**
     * DiscardOldestPolicy:当有任务添加到线程池被拒绝时，线程池会丢弃阻塞队列中末尾的任务，然后将被拒绝的任务添加到末尾(同步队列是先进先出的)。
     */
    public static void discardOldestPolicyDemo() {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"DiscardOldestPolicy"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        // 新建10个任务，并将它们添加到线程池中。
        for (int i = 0; i < 10; i++) {
            Runnable myrun = new MyRunnable("task-" + i);
            pool.execute(myrun);
        }
        // 关闭线程池
        pool.shutdown();

    }

    /**
     * 当有任务添加到线程池被拒绝时，线程池会将被拒绝的任务添加到”线程池正在运行的线程”中去运行。
     */
    public static void callerRunsPolicyDemo() {

        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"CallerRunsPolicy"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 新建10个任务，并将它们添加到线程池中。
        for (int i = 0; i < 10; i++) {
            Runnable myrun = new MyRunnable("task-" + i);
            pool.execute(myrun);
        }

        // 关闭线程池
        pool.shutdown();

    }


    public static void main(String[] args) {
        callerRunsPolicyDemo();
    }


}
