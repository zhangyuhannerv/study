package com.study.zyh.javase.java_Lambda;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @ClassName _7ConstructorRefTest
 * @Description 构造器引用
 * 一.构造器引用
 * 和方法引用类似，函数式接口的抽像方法的形参列表和构造器的形参列表一致
 * 抽像方法的返回值类型即为构造器所属的类
 * @Author Zhangyuhan
 * @Date 2021/10/26
 * @Version 1.0
 */
public class _7ConstructorRefTest {
    @Test
    // Supplier中的T get()
    // _0Employee的空参构造器:Employee()
    public void test1() {
        // 构造器引用
        Supplier<_0Employee> sup0 = new Supplier<_0Employee>() {
            @Override
            public _0Employee get() {
                return new _0Employee();
            }
        };
        System.out.println("*************");
        Supplier<_0Employee> sup1 = () -> new _0Employee();


        System.out.println("************");
        Supplier<_0Employee> sup2 = _0Employee::new;// 可以看成是空参的构造方法的引用
        _0Employee employee = sup2.get();
        employee.setName("张三");
        System.out.println(employee.getName());
    }

    @Test
    // Function中的 R apply(T t)
    public void test2() {
        Function<Integer, _0Employee> fun = id -> new _0Employee(id);
        _0Employee apply = fun.apply(1001);
        System.out.println(apply);

        System.out.println("*************");
        Function<Integer, _0Employee> fun1 = _0Employee::new;// 方法引用不需要写参数，此时引用的是传id的构造器
        _0Employee apply1 = fun1.apply(1002);
        System.out.println(apply1);
    }

    @Test
    //BiFunction R apply(T t,U u)
    public void test3() {
        BiFunction<Integer, String, _0Employee> fun1 = (id, name) -> new _0Employee(id, name);
        System.out.println(fun1.apply(1001, "张三"));

        System.out.println("****************");

        BiFunction<Integer, String, _0Employee> fun2 = _0Employee::new;
        System.out.println(fun2.apply(1002, "李四"));
    }

}

