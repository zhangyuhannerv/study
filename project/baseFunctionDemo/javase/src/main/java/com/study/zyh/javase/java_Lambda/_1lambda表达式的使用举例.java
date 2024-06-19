package com.study.zyh.javase.java_Lambda;

import org.junit.Test;

import java.util.Comparator;

/**
 * 664-690
 *
 * @ClassName _1lambda表达式的使用举例
 * @Description lambda的使用举例
 * @Author Zhangyuhan
 * @Date 2021/10/8
 * @Version 1.0
 */
public class _1lambda表达式的使用举例 {
    public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("我爱北京天安门");
            }
        };
        r1.run();

        System.out.println("************");

        Runnable r2 = () -> System.out.println("我爱北京故宫");

        r2.run();
    }

    @Test
    public void test2() {
        // 以比较器为例
        // 通常写法
        Comparator<Integer> com1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        System.out.println(com1.compare(12, 29));// 返回-1，说明前面的数小

        System.out.println("************");

        // lambda表达式
        Comparator<Integer> com2 = (o1, o2) -> Integer.compare(o1, o2);

        System.out.println(com2.compare(30, 19));

        System.out.println("************");

        // 方法引用
        Comparator<Integer> com3 = Integer::compare;

        System.out.println(com3.compare(33, 33));
    }
}
