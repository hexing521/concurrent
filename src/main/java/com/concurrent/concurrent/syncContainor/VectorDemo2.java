package com.concurrent.concurrent.syncContainor;

import java.util.Iterator;
import java.util.Vector;

/**
 * 【同步容器遇到的问题】
 */
public class VectorDemo2 {

    //todo: java.util.ConcurrentModificationException
    private static void test1(Vector<Integer> v1) { // foreach
        for (Integer i : v1) {
            if (i.equals(3)) {
                v1.remove(i); //TODO：在遍历集合的时候进行update，remove操作触发的异常
            }
        }
    }

    //todo: java.util.ConcurrentModificationException
    private static void test2(Vector<Integer> v1) { // iterator
        Iterator<Integer> iterator = v1.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            if (i.equals(3)) {
                v1.remove(i);
            }
        }
    }

    //todo: success
    private static void test3(Vector<Integer> v1) { // for
        for (int i = 0; i < v1.size(); i++) {
            if (v1.get(i).equals(3)) {
                v1.remove(i);
            }
        }
    }

    public static void main(String[] args) {

        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        test1(vector);
    }
}
