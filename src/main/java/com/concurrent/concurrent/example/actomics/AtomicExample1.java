package com.concurrent.concurrent.example.actomics;


import java.util.concurrent.atomic.AtomicReference;

public class AtomicExample1 {

    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        //如果期望值是0 则将count的值修改为2
        count.compareAndSet(0, 2); // 2
        count.compareAndSet(0, 1); // no
        count.compareAndSet(1, 3); // no
        count.compareAndSet(2, 4); // 4
        count.compareAndSet(3, 5); // no
        System.out.println("count:"+count.get());
    }
}
