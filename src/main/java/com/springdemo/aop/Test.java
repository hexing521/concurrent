package com.springdemo.aop;

import com.springdemo.aop.introduced.DefaultEncoreable;
import com.springdemo.aop.introduced.Encoreable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConcertConfig.class)
public class Test {
    @Autowired
    private Performance p;

    @Autowired
    private Performance musicPerformance;

    @org.junit.Test
    public void testAudience() {
        p.perform();
    }

    @org.junit.Test
    public void testIntroduced() {
        Encoreable encoreable = (Encoreable) musicPerformance; //使用方法
        encoreable.performEncore();
    }

}
