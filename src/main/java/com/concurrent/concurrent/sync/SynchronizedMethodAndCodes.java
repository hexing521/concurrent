package com.concurrent.concurrent.sync;

class TestSynchronized {
    //对象锁
    public void test1() {
        synchronized (this) {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }

            }
        }
    }

    //方法锁
    public synchronized void test2() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

        }
    }

    //无锁
    public void test3() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

        }
    }

    //静态方法锁
    public static synchronized void test4() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

        }
    }

    //类锁
    public static void test5() {
        synchronized (TestSynchronized.class) {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }

            }
        }
    }


}

/**
 * 第一种情况:test1和test2(对象锁和方法锁的比较)
 * test1方法使用的是synchornized锁代码块的方式，并且括号内传入的是this,也就是说锁的是该对象，
 * 而test2是使用synchornized修饰方法，则锁的是对象实例,所以th1和th2竞争的是同一把锁
 * <p>
 * 第二种情况: test1和test3(加锁的方法和没加锁的方法比较)
 * test1锁定的是this对象，而test3没有锁，同步的方法（加锁方法）和没有进行同步的方法（普通方法）是互不影响的，一个线程进入了同步方法，
 * 得到了对象锁，其他线程还是可以访问那些没有同步的方法（普通方法）
 * <p>
 * 第三种情况：test4和test5(静态方法锁和类锁的比较)
 * test4是静态方法锁，静态方法锁锁的是当前类的class对象，test5是类锁，所以test4和test5竞争的是同一把锁
 * <p>
 * 第四种情况：test2和test4(静态方法锁和非静态方法锁比较)
 * test2是非静态方法锁,test4是静态方法锁，运行结果是交替进行的，这证明了类锁和对象锁是两个不一样的锁，
 * 控制着不同的区域，它们是互不干扰的。同样，线程获得对象锁的同时，也可以获得该类锁，即同时获得两个锁，这是允许的。
 * <p>
 * 一个类的对象锁和另一个类的对象锁是没有关联的，当一个线程获得A类的对象锁时，它同时也可以获得B类的对象锁。
 */
public class SynchronizedMethodAndCodes {

    public static void main(String[] args) {
        TestSynchronized test = new TestSynchronized();
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.test4();
            }
        }, "test4");


        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.test2();
            }
        }, "test2");

        th1.start();
        th2.start();

    }


}
