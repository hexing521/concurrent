package com.concurrent.concurrent.singleton;

/**
 * 饿汉模式  枚举类来保证线程安全 最佳方法
 */
public class SingletonExmp4 {

    private SingletonExmp4() {
    }


    //todo 静态工厂方法
    public static SingletonExmp4 getInstance() {
        return Singleton.INSTSNCE.getInstance();
    }

    /**
     * 枚举类
     */
    private enum Singleton {
        INSTSNCE;

        //todo 单例对象
        private SingletonExmp4 instance = null;

        //todo JVM保证这个方法绝对只调用一次
        Singleton() {
            instance = new SingletonExmp4();
        }

        public SingletonExmp4 getInstance() {
            return instance;
        }

    }

}

