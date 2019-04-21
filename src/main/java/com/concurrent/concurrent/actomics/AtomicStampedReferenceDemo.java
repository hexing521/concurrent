package com.concurrent.concurrent.actomics;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Java中使用AtomicStampedReference来解决CAS中的ABA问题，它不再像compareAndSet方法
 * 中只比较内存中的值也当前值是否相等，而且先比较引用是否相等，然后比较值是否相等，这样就避免了ABA问题
 */
public class AtomicStampedReferenceDemo {

    public static void main(String[] args) {

        String str1 = "aaa";
        String str2 = "bbb";
        AtomicStampedReference<String> reference = new AtomicStampedReference<String>(str1, 1);
        reference.compareAndSet(str1, str2, reference.getStamp(), reference.getStamp() + 1);
        System.out.println("reference.getReference() = " + reference.getReference());

        boolean b = reference.attemptStamp(str2, reference.getStamp() + 1);
        System.out.println("b: " + b);
        System.out.println("reference.getStamp() = " + reference.getStamp());

        boolean c = reference.weakCompareAndSet(str2, "ccc", 4, reference.getStamp() + 1);
        System.out.println("reference.getReference() = " + reference.getReference());
        System.out.println("c = " + c);


    }
}
