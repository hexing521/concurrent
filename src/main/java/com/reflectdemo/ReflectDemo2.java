package com.reflectdemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * java反射实例
 */
public class ReflectDemo2 {


    public static void ff() {
        Class c1 = int.class;//int 的类类型
        Class c2 = String.class;//String类的类类型
        Class c3 = void.class;
        System.out.println(c1.getName());
        System.out.println(c2.getName());
        System.out.println(c2.getSimpleName());
        System.out.println(c3.getName());
    }

    //todo  方法的反射
    public static void printClassMethodMessage(String classFullName) {
        //要获取类的信息》》首先我们要获取类的类类型
        //Class c = obj.getClass();  //获取类类型方法一
        Class c = null; //获取类类型方法二
        try {
            c = Class.forName(classFullName);//类的全称
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //我们知道Object类是一切类的父类，所以我们传递的是哪个子类的对象，c就是该子类的类类型。
        //接下来我们要获取类的名称
        System.out.println("类的名称是:" + c.getName());
        /*
         * 在java里面，方法也是对象，方法是Method类的对象
         *一个成员方法就是一个Method的对象，那么Method就封装了对这个成员方法的操作
         */
        //如果我们要获得所有的方法，可以用getMethods()方法，这个方法获取的是所有的Public的函数，包括父类继承而来的。
        //如果我们要获取所有该类自己声明的方法，就可以用getDeclaredMethods()方法，这个方法是不问访问权限的。
        Method[] ms = c.getMethods();
        Method[] ms2 = c.getDeclaredMethods();
        //接下来我们拿到这些方法之后干什么？我们就可以获取这些方法的信息，比如方法的名字。
        //首先我们要循环遍历这些方法
        for (int i = 0; i < ms.length; i++) {
            //然后可以得到方法的返回值类型的类类型
            Class returnType = ms[i].getReturnType();
            //得到方法的返回值类型的名字
            System.out.print(returnType.getName() + " ");
            //得到方法的名称
            System.out.print(ms[i].getName() + "(");
            //获取参数类型--->得到的是参数列表的类型的类类型
            Class[] paramTypes = ms[i].getParameterTypes();
            for (Class class1 : paramTypes) {
                System.out.print(class1.getName() + ",");
            }
            System.out.println(")");
        }
    }


    //todo 成员变量的反射
    public static void printFieldMessage(Object obj) {
        Class c = obj.getClass();
        //获取所有的自己所有的public的成员变量以及继承父类的成员变量
        Field[] fs1 = c.getFields();
        //获取所有的自己所有的public的成员变量
        Field[] fs2 = c.getDeclaredFields();
    }

    //todo 构造函数的反射
    public static void printConMessage(Object obj) {
        Class c = obj.getClass();
        /*
         * 首先构造函数也是对象，是java.lang.Constructor类的对象
         * 也就是java.lang. Constructor中封装了构造函数的信息
         * 和前面说到的一样，它也有两个方法：
         * getConstructors()方法获取所有的public的构造函数
         * getDeclaredConstructors()方法得到所有的自己声明的构造函数
         */
        //Constructor[] cs = c.getConstructors();
        Constructor[] cs = c.getDeclaredConstructors();
        for (Constructor constructor : cs) {
            //我们知道构造方法是没有返回值类型的，但是我们可以：
            System.out.print(constructor.getName() + "(");
            //获取构造函数的参数列表》》得到的是参数列表的类类型
            Class[] paramTypes = constructor.getParameterTypes();
            for (Class class1 : paramTypes) {
                System.out.print(class1.getName() + ",");
            }
            System.out.println(")");
        }
    }


    public static void main(String[] args) {
        //通过类的类类型获取类的实例 java.lang.String
        printClassMethodMessage("com.reflectdemo.ReflectDemo");

    }

}

