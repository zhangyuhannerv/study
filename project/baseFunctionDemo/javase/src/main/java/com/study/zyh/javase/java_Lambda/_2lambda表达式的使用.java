package com.study.zyh.javase.java_Lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @ClassName _2lambda表达式的使用
 * @Description 1.举例：(o1,o2)->Integer.compare(o1,o2)
 * 2.分析：
 * ->:lambda操作符或者箭头操作符
 * 左边：lambda的形参列表，其实就是接口中的抽像方法的形参列表
 * 右边：lambda体，其实就是重写的抽像方法的方法体
 * 3.本质：
 * Lambda的实质就是函数式接口的实例
 * 4.如果一个接口种只声明了一个抽像方法，则此接口就称为函数式接口
 * 5.以前写的匿名实现类都可以用lambda表示
 * @Author Zhangyuhan
 * @Date 2021/10/9
 * @Version 1.0
 */
public class _2lambda表达式的使用 {
    // lambda表达式的本质：作为接口的实例（接口的具体实现类的对象）
    // 3.lambda的使用：（6种情况）

    // 1.无参，无返回值
    @Test
    public void test1() {
        Runnable r2 = () -> {
            System.out.println("我爱北京故宫");
        };
        r2.run();
    }

    // 2.有参，无返回值
    @Test
    public void test2() {
        Consumer<String> con = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        con.accept("123");

        System.out.println("************");

        Consumer<String> con1 = (String s) -> {
            System.out.println(s);
        };
        con1.accept("abc");
    }

    // 3.数据类型可以省略，因为可以由编译器推断得出，称为'类型推断'
    @Test
    public void test3() {
        Consumer<String> con1 = (String s) -> {
            System.out.println(s);
        };
        con1.accept("abc");
        System.out.println("*****************");
        Consumer<String> con2 = (s) -> {
            System.out.println(s);
        };
        con2.accept("123");

        ArrayList<String> list = new ArrayList<>();// 后面没有泛型，类型推断
        int[] arr = {1, 2, 3};// 类型推断
    }

    // 4.lambda若只需要一个参数时，参数的小括号可以省略
    @Test
    public void test4() {
        Consumer<String> con2 = (s) -> {
            System.out.println(s);
        };
        con2.accept("123");

        System.out.println("*****************");

        Consumer<String> con3 = s -> {
            System.out.println(s);
        };
        con3.accept("123");
    }

    // 5.lambda需要有两个或者以上的参数的时候，多条语句执行的时候，并且可以有返回值
    @Test
    public void test5() {
        Comparator<Integer> com1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                System.out.println(o1);
                System.out.println(o2);
                return o1.compareTo(o2);
            }
        };
        System.out.println(com1.compare(12, 21));
        System.out.println("**********");

        Comparator<Integer> com2 = (o1, o2) -> {
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        };
        System.out.println(com2.compare(6, 2));
    }

    // 6.当lambda只有一条语句的时候，return与大括号若有，都可以省略
    @Test
    public void test7() {
        Comparator<Integer> com2 = (o1, o2) -> {
            return o1.compareTo(o2);
        };
        System.out.println(com2.compare(6, 2));

        System.out.println("*************************");

        Comparator<Integer> com3 = (o1, o2) -> o1.compareTo(o2);

        System.out.println(com3.compare(12, 21));

        System.out.println("*************************");

        Consumer<String> consumer = o -> System.out.println(o);
        consumer.accept("afdfadfdafdf");
    }

    /**
     * 总结：
     * 左边：lambda的形参列表中的参数类型可以省略（类型推断)，如果只有一个参数，那么()可以省略，没有参数或者两个及以上()不要省略
     * 右边：lambda体应该使用{}进行包裹,如果，lambda的体只有一条执行语句，无论是不是return语句，都可以省略{}，同时如果是return语句的话
     * 可以省略return关键字（注意省略{}必须省略return，不能省略{}却写上了return，这样编译器会报错)
     */

}
