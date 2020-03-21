package com.springdemo.aop;

/**
 * 为Performance创建代理
 * 并在perform()方法调用前或者调用后应用切面中的通知方法。
 */
public interface Performance {
    public void perform();
}
