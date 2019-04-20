package com.concurrent.concurrent.threadpool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 【Future】
 * 线程池提交线程获取线程返回数据方法
 * ref:https://blog.csdn.net/qq_25806863/article/details/71214033
 */
public class FutureDemo {
    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    /**
     * 使用ExecutorService的submit(Callable task)提交线程;
     * 并使用future返回线程的执行结果
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void callableDemo() throws InterruptedException, ExecutionException {
        //创建一个Callable，3秒后返回String类型
        Callable myCallable = new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                System.out.println("calld方法执行了");
                return "call方法返回值";
            }
        };
        System.out.println("提交任务之前 " + getStringDate());
        Future future = executor.submit(myCallable);
        System.out.println("提交任务之后，获取结果之前 " + getStringDate());
        //todo 通过executor.submit提交一个Callable，返回一个Future，然后通过这个Future的get方法取得返回值。该方法是阻塞性的
        System.out.println("获取返回值: " + future.get());
        System.out.println("获取到结果之后 " + getStringDate());
    }

    /**
     * 使用submit(Runnable task)提交线程
     * 因为Runnable是没有返回值的，所以如果submit一个Runnable的话，get得到的为null：
     */
    public static void RunnableDemo() throws InterruptedException, ExecutionException {
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " run time: " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Future future = executor.submit(myRunnable);
        System.out.println("获取的返回值： " + future.get());
    }


    /**
     * 使用submit(Runnable task, T result)提交线程
     * 虽然submit传入Runnable不能直接返回内容，但是可以通过submit(Runnable task, T result)传入一个载体，通过这个载体获取返回值。这个其实不能算返回值了，是交给线程处理一下。
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void runnableDemo2() throws InterruptedException, ExecutionException {
        Data data = new Data();
        Future<Data> future = executor.submit(new MyThread(data), data);
        System.out.println("返回的结果  name: " + future.get().getName() + ", sex: " + future.get().getSex());
        System.out.println("原来的Data  name: " + data.getName() + ", sex: " + data.getSex());
    }


    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    public static void main(String[] args) {

        try {
            callableDemo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}

class MyThread implements Runnable {
    private Data data;

    public MyThread(Data name) {
        this.data = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("线程  执行:");
            data.setName("新名字");
            data.setSex("新性别");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Data {
    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}