package com.concurrent.concurrent.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用Synchronized修饰代码块和方法
 */
public class SynchronizedExem1 {

    /**
     * 修饰一个代码块
     * @param type
     */
    public void test1(int type) {
        synchronized (this) {
            for (int i = 0; i <= 10; i++) {
                System.out.println("test1==="+type+"==" + i);
            }
        }
    }

    /**
     * 如果一个子类调用一个父类的带有synchronized修饰的方法，那么子类无法带synchronized的关键字，
     * 因为synchronized的关键字不属于方法声明的一部分
     * 如果子类调用父类的方法需要加同步锁，则需要子类自己在方法上声明synchronized关键字
     */
    public synchronized void test2(int type) {
        for (int i = 0; i <= 10; i++) {
            System.out.println("test2==="+type+"==" + i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExem1 exem1 = new SynchronizedExem1();
        SynchronizedExem1 exem2 = new SynchronizedExem1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                exem1.test1(1);
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                exem2.test1(2);
            }
        });



    }

}
