package com.concurrent.concurrent.singleton;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行实例化 延迟加载
 * 使用double check + volatile 实现懒汉模式的线程安全
 * volatile 关键字禁止cpu的指令重排序，
 * 在单线程的情况下无论指令如何重排序都不会出现线程不安全的情况，但是在多线程的情况下发生指令重排序是有可能导致线程不安全的
 *
 * 指令重排序：
 *  1、memory = allocate() 分配对象的内存空间
 * 2、ctorInstance() 初始化对象
 * 3、instance = memory 设置instance指向刚分配的内存
 *
 * JVM和cpu优化，发生了指令重排
 * 1、memory = allocate() 分配对象的内存空间
 * 3、instance = memory 设置instance指向刚分配的内存
 * 2、ctorInstance() 初始化对象
 *
 */
public class SingletonExmp3 {

    private SingletonExmp3() {
    }

    //todo 单例对象
    private volatile static SingletonExmp3 instance = null;

    //todo 静态工厂方法
    public static SingletonExmp3 getInstance() {
        if (instance == null) {  //TODO 双重验证  可提升多线程下的执行性能 避免多个线程排队等待
            synchronized (SingletonExmp3.class) { //TODO 添加同步锁
                if (instance == null) {  //TODO 双重验证
                    instance = new SingletonExmp3();
                }
            }
        }
        return instance;
    }

}

