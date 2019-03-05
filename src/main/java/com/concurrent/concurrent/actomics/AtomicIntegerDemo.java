package com.concurrent.concurrent.actomics;

/**
 * AtomicInteger
 * 这个原子性的操作是利用unsafe本地方法和volatile来实现的
 * volatile每次拿到的都是主存里数据
 * <p>
 * 1，Unsafe，是CAS的核心类，由于Java方法无法直接访问底层系统，需要通过本地（native）方法来访问，Unsafe相当于一个后门，基于该类可以直接操作特定内存的数据。
 * 2，变量valueOffset，表示该变量值在内存中的偏移地址，因为Unsafe就是根据内存偏移地址获取数据的。
 * 3，变量value用volatile修饰，保证了多线程之间的内存可见性。
 * <p>
 * <p>
 * private static final Unsafe unsafe = Unsafe.getUnsafe();
 * private static final long valueOffset;
 * static {
 * try {
 * valueOffset = unsafe.objectFieldOffset
 * //这里通过反射获取AtomicInteger的偏移量，而unsafe就是通过内存偏移量来获取数据的
 * (AtomicInteger.class.getDeclaredField("value"));
 * } catch (Exception ex) { throw new Error(ex); }
 * }
 * <p>
 * private volatile int value;
 */
public class AtomicIntegerDemo {
}
