package com.concurrent.concurrent.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用Synchronized修饰类和静态方法
 */
public class SynchronizedExem2 {

    /**
     * 修饰一个类
     */
    public static void test1() {
        synchronized (SynchronizedExem2.class) {
            for (int i = 0; i <= 10; i++) {
                System.out.println("test1=====" + i);
            }
        }
    }


    /**
     * 修饰一个静态方法
     * @param type
     */
    public static synchronized void test2(int type) {
        for (int i = 0; i <= 10; i++) {
            System.out.println("test2===" + type + "==" + i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExem2 exem1 = new SynchronizedExem2();
        SynchronizedExem2 exem2 = new SynchronizedExem2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            exem1.test2(1);
        });
        executorService.execute(() -> {
            exem1.test2(2);
        });
    }

}
