package com.study.zyh.javase.java_genericity.generic_class;

/**
 * @ClassName GenericClass
 * @Description 泛型类
 * 泛型类的定义语法：
 * class 类名称<泛型标识1，泛型标识2...>{
 * private 泛型标识1 变量名;
 * }
 * 常用的泛型标识：T,E,K,V
 * K,V常用于集合，如HashMap
 * @Author Zhangyuhan
 * @Date 2021/12/3
 * @Version 1.0
 */


/**
 * 简单的泛型类的定义
 *
 * @param <T> 泛型标识：类型形参
 *            在创建对象的时候来指定具体的数据类型
 */
public class GenericClass<T> {
    // T是由外部使用该类的时候来指定的
    private T t;

    public GenericClass() {
    }

    public GenericClass(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "GenericClass{" +
                "t=" + t +
                '}';
    }
}
