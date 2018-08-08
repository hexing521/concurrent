package com.concurrent.concurrent.actomics;


import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdater主要是用来更新某个类中的字段
 * 而且这个字段必须volatile修饰的，线程可见的，同时还不能是static字段
 *
 */
public class AtomicExample2 {

    private static AtomicIntegerFieldUpdater<AtomicExample2> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicExample2.class, "count");

    public  volatile int count = 100;

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {

        AtomicExample2 example5 = new AtomicExample2();

        if (updater.compareAndSet(example5, 100, 120)) {
            System.out.println("update success 1" + example5.getCount());
        }

        if (updater.compareAndSet(example5, 100, 120)) {
            System.out.println("update success 2" + example5.getCount());
        } else {
            System.out.println("update failed" + example5.getCount());
        }
    }
}
