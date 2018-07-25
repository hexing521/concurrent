package com.concurrent.concurrent.singleton;

/**
 * 饿汉模式
 * 单例实例在进行类装载的时候进行实例化
 * 这种做法的缺点是不管次类是否能用的上都要进行初始化
 * 缺点：
 * 1，如果用不上那么就会造成资源的浪费
 * 2，类在加载的时候就要进行初始化，如果在类构造器里面需要完成比较复杂沉重的业务代码 那么就会对性能方面产生影响
 * 优点:  是线程安全的
 */
public class SingletonExmp2 {

    private SingletonExmp2() {
    }

    //todo 初始化单例对象
    private static SingletonExmp2 instance = new SingletonExmp2();

    //todo 静态工厂方法
    public static SingletonExmp2 getInstance() {
        return instance;
    }


}
