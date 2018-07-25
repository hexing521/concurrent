package com.concurrent.concurrent.singleton;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行实例化 延迟加载
 * 这种方式是非线程安全的
 */
public class SingletonExmp1 {

    private SingletonExmp1() {
    }
    //todo 单例对象
    private static SingletonExmp1 instance = null;

    //todo 静态工厂方法
    public static SingletonExmp1 getInstance() {
        if (instance == null) {
            instance = new SingletonExmp1();
        }
        return instance;
    }

}

