package com.reflectdemo;


class F {
    void print() {
    }
}

public class ReflectDemo {

    private String var_1="var_1";
    public String var_2="var_2";

    //todo 获取类的类类型三种方法
    private static void demo() {
        F f = new F();
        //第一种表达方式
        //这种表达方式同时也告诉了我们任何一个类都有一个隐含的静态成员变量class
        Class c1 = F.class;
        //第二种表达方式
        //这种表达方式在已知了该类的对象的情况下通过getClass方法获取
        Class c2 = f.getClass();
        //第三种表达方式
        Class c3 = null;
        try {
            c3 = Class.forName("com.reflectdemo.F");//类的全称
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(c1 == c2);
        System.out.println(c1 == c3);
    }


//    public static void Demo1() {
//        try {
//            Foo foo = (Foo) c1.newInstance();//foo就表示F类的实例化对象
//            foo.print();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }


    public static void main(String[] args) {
        demo();
    }
}
