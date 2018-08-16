package com.concurrent.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 【FutureTask的使用】
 */
@Slf4j
public class FutureTaskExample {

    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("do something in callable");
                Thread.sleep(5000);
                return "Done";
            }
        });

        new Thread(futureTask).start();//TODO 启动线程
        log.info("do something in main");
//        futureTask.cancel(true);//TODO 并非会强制停止线程，cancel方法内部调用了interrupt()方法，所以它只能停止有sleep,wait,join这些方法的调用线程
        Thread.sleep(1000);
        String result = futureTask.get();//TODO 阻塞线程，等待线程返回结果数据
        log.info("result：{}", result);
    }
}
