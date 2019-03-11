package com.concurrent.concurrent.thread;


/**
 * Thread的wait()和sleep()区别
 */
public class ThreadWaitAndSleepDemo implements Runnable {
    int number = 10;

    public void firstMethod() throws Exception {
        synchronized (this) {
            number += 100;
            System.out.println(Thread.currentThread().getName() + "--" + number);
        }
    }

    public void secondMethod() throws Exception {
        synchronized (this) {
            /**
             * (休息2S,阻塞线程)
             * 以验证当前线程对象的机锁被占用时,
             * 是否被可以访问其他同步代码块
             */
            Thread.sleep(2000);//todo 线程暂停未释放锁
//            this.wait(2000); //todo 线程暂停并释放锁
            number *= 200;
            System.out.println(Thread.currentThread().getName() + "--" + number);
        }
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void run() {
        try {
            firstMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) throws Exception {
//        for (int i = 0; i < 20; i++) {
        ThreadWaitAndSleepDemo demo = new ThreadWaitAndSleepDemo();
        Thread thread = new Thread(demo);
        thread.start();
        demo.secondMethod();
        System.out.println(Thread.currentThread().getName() + "--" + demo.number);
//        }

    }


}
