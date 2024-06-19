package com.study.zyh.javase.java_Lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * @ClassName _6MethodRefTest3
 * @Description 方法引用情况3：类::非静态方法(实例方法)
 * @Author Zhangyuhan
 * @Date 2021/10/25
 * @Version 1.0
 */
public class _6MethodRefTest3 {
    @Test
    public void test1() {
        Comparator<String> com1 = (s1, s2) -> s1.compareTo(s2);
        System.out.println(com1.compare("abc", "abd"));
        System.out.println("**********************");
        Comparator<String> com2 = String::compareTo;// 第一个参数是方法的调用者
        System.out.println(com2.compare("123", "012"));
    }

    @Test
    public void test2() {
        BiPredicate<String, String> pre1 = (s1, s2) -> s1.equals(s2);
        System.out.println(pre1.test("abc", "abc"));
        System.out.println("************");
        BiPredicate<String, String> pre2 = String::equals;
        System.out.println(pre2.test("123", "124"));
    }

    @Test
    public void test3() {
        _0Employee e = new _0Employee();
        e.setName("张三");
        Function<_0Employee, String> fun1 = (a) -> a.getName();
        System.out.println(fun1.apply(e));

        System.out.println("***********");

        Function<_0Employee, String> fun2 = _0Employee::getName;
        System.out.println(fun2.apply(e));
    }
}
