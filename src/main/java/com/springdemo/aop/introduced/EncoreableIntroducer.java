package com.springdemo.aop.introduced;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

/**
 * EncoreableIntroducer是一个切面，和其他切面不同在于它没有定义各种通知，
 * 它通过@DeclareParents注解将Encoreable接口引入到Performance接口的实现中
 */
@Aspect
public class EncoreableIntroducer {

    @DeclareParents(value = "com.springdemo.aop.Performance+",
            defaultImpl = DefaultEncoreable.class)
    public static Encoreable encoreable;
}
