package com.springdemo.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 定义切面
 * 会为Performance接口创建代理，并在perform()方法调用前或者调用后应用切面中的通知方法
 * https://www.jianshu.com/p/8b95db8d7a1f
 */
@Aspect
public class Audience {

    //可直接定义重复的切点
//    @Pointcut("execution(* com.spring.sample.concert.Performance.perform( .. ))")
//    public void performance() {}


    /**
     * 前置通知
     */
    @Before("execution(* com.springdemo.aop.Performance.perform( .. ))")
    public void silenceCellPhones() {
        System.out.println("Silencing cell phones");
    }

    /**
     * 前置通知
     */
    @Before("execution(* com.springdemo.aop.Performance.perform( .. ))")
    public void takeSeats() {
        System.out.println("Taking seats");
    }

    /**
     * 方法结束后被调用
     */
    @AfterReturning("execution(* com.springdemo.aop.Performance.perform( .. ))")
    public void applause() {
        System.out.println("CLAP CLAP CLAP!!!");
    }

    /**
     * 方法抛出异常后被调用
     */
    @AfterThrowing("execution(* com.springdemo.aop.Performance.perform( .. ))")
    public void demandRefund() {
        System.out.println("Demand a refund");
    }

    /**
     * 环绕通知本质上是将前置通知、后置通知和异常通知整合成一个单独的通知
     * 这个方法实现的效果跟之前的四个函数完全相同
     *
     * @param joinPoint
     */
    @Around("execution(* com.springdemo.aop.Performance.perform( .. ))")
    public void watchPerformance(ProceedingJoinPoint joinPoint) {
        try {
            System.out.println("Silencing cell phones");
            System.out.println("Taking seats");
            joinPoint.proceed();
            System.out.println("CLAP CLAP CLAP!!!");
        } catch (Throwable e) {
            System.out.println("Demanding a refund");
        }
    }
}
