package com.springdemo.springbeanlifecycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring bean 生命周期演示
 * <p>
 * 1、Bean自身的方法：这个包括了Bean本身调用的方法和通过配置文件中<bean>的init-method和destroy-method指定的方法
 * 2、Bean级生命周期接口方法：这个包括了BeanNameAware、BeanFactoryAware、InitializingBean和DiposableBean这些接口的方法
 * 3、容器级生命周期接口方法：这个包括了InstantiationAwareBeanPostProcessor 和 BeanPostProcessor 这两个接口实现，一般称它们的实现类为“后处理器”。
 * 4、工厂后处理器接口方法：这个包括了AspectJWeavingEnabler, ConfigurationClassPostProcessor, CustomAutowireConfigurer等等非常有用的工厂后处理器
 * 接口的方法。工厂后处理器也是容器级的。在应用上下文装配配置文件之后立即调用。
 *
 * https://www.cnblogs.com/zrtqsk/p/3735273.html
 */
public class BeanLifeCycle {

    public static void main(String[] args) {

        System.out.println("--现在开始初始化容器");

        ApplicationContext factory = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("--容器初始化成功");
        //得到Preson，并使用
        Person person = factory.getBean("person", Person.class);
        System.out.println(person);

        System.out.println("--现在开始关闭容器！");
        ((ClassPathXmlApplicationContext) factory).registerShutdownHook();
    }

}
