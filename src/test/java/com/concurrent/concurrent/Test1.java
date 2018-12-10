package com.concurrent.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Person {
    private String name;

    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

public class Test1 {
    public static void main(String[] args) {

        Map<String, Person> persionMap = new HashMap<String, Person>();
        persionMap.put("张三", new Person("张三",21));
        persionMap.put("李四", new Person("李四",19));
        persionMap.put("王五", new Person("张三",25));
        persionMap.put("赵六", new Person("张三",26));
        persionMap.put("孙七", new Person("张三",32));


    }

}