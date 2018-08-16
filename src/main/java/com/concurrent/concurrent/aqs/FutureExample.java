package com.concurrent.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 【Future的使用】
 *  Callable:  线程结束后可以获取返回结果，抛出异常
 *  Future接口: 取消，查询是否被取消，查询是否完成，获取结果等，监控其他的线程
 *  FutureTask: 聚合了Runnable和Future
 *
 */
@Slf4j
public class FutureExample {

    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            Thread.sleep(5000);
            return "Done";
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new MyCallable());
        log.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();//TODO 阻塞知道拿到线程的结果数据
        log.info("result：{}", result);
    }
}
