package com.concurrent.concurrent.immutable;




import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 【不可变对象】
 * [不可变对象需要满足的条件]：
 * 1，对象创建后其状态不能修改
 * 2，对象所有域都是final类型
 * 3，对象是正确创建的，在对象创建期间，this引用没有溢出
 *
 * Final修改的变量
 */
public class ImmutableExample1 {

    private final static Integer a = 1;
    private final static String b = "2";
    private final static Map<Integer, Integer> map = Maps.newHashMap();//TODO 非线程安全的

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
    }

    public static void main(String[] args) {
//        a = 2;
//        b = "3";
//        map = Maps.newHashMap(); //todo 不可以重新引用
        map.put(1, 3); //todo 可以修改map key所对应的value值
        System.out.println(map.get(1));
    }

    private void test(final int a) {
//        a = 1;
    }
}
