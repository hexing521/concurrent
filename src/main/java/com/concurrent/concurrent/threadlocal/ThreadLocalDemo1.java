package com.concurrent.concurrent.threadlocal;

/**
 * 【ThreadLocal】
 * ThreadLocal的应用:
 * 在多线程的环境下对资源的使用（ps:数据库的链接 connection）：
 * 1,需要保证一个connection是线程安全的，不会出现一个线程获取到了一个链接之后，这个链接被另一个线程close掉
 * 2,不对应用服务引起性能损耗（频繁的建立连接和关闭链接）
 *
 * 【ThreadLocal解决方法】:
 * ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
 *
 * 【ThreadLocal深入分析】:
 * get()方法是用来获取ThreadLocal在当前线程中保存的变量副本，
 * set()用来设置当前线程中变量的副本，
 * remove()用来移除当前线程中变量的副本，
 * initialValue()是一个protected方法，一般是用来在使用时进行重写的，它是一个延迟加载方法
 *
 *【总结】：
 * 1）实际的通过ThreadLocal创建的副本是存储在每个线程自己的threadLocals中的；
 * 2）为何threadLocals的类型ThreadLocalMap的键值为ThreadLocal对象，因为每个线程中可有多个threadLocal变量，就像上面代码中的longLocal和stringLocal；
 * 3）在进行get之前，必须先set，否则会报空指针异常；
 * 如果想在get之前不需要调用set就能正常访问的话，必须重写initialValue()方法。
 * 因为在上面的代码分析过程中，我们发现如果没有先set的话，即在map中查找不到对应的存储，则会通过调用setInitialValue方法返回i，而在setInitialValue方法中
 * 有一个语句是T value = initialValue()， 而默认情况下，initialValue方法返回的是null
 * http://www.cnblogs.com/dolphin0520/p/3920407.html
 */
public class ThreadLocalDemo1 {

    //声明ThreadLocal
    ThreadLocal<Long> longLocal = new ThreadLocal<Long>();
    ThreadLocal<String> stringLocal = new ThreadLocal<String>();


    public void set() {
        longLocal.set(Thread.currentThread().getId());
        stringLocal.set(Thread.currentThread().getName());
    }

    public long getLong() {
        return longLocal.get();
    }

    public String getString() {
        return stringLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final ThreadLocalDemo1 test = new ThreadLocalDemo1();


        test.set();
        System.out.println(test.getLong());
        System.out.println(test.getString());


        Thread thread1 = new Thread() {
            public void run() {
                test.set();
                System.out.println(test.getLong());
                System.out.println(test.getString());
            }
        };
        thread1.start();
        thread1.join();

        System.out.println(test.getLong());
        System.out.println(test.getString());
    }
}
