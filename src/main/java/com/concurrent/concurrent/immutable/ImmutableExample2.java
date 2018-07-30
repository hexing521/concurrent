package com.concurrent.concurrent.immutable;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * 【不可变对象】
 * 使用Collections.unmodifiableMap 是对象无法被修改  线程安全
 */
public class ImmutableExample2 {

    private static Map<Integer, Integer> map = Maps.newHashMap();//TODO 线程安全

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(1, 3);
        System.out.println(map.get(1));
    }

}
