package com.springdemo.aop.introduced;

import org.springframework.stereotype.Component;

@Component
public class DefaultEncoreable implements Encoreable {
    @Override
    public void performEncore() {
        System.out.println("perform the encore!");
    }
}
