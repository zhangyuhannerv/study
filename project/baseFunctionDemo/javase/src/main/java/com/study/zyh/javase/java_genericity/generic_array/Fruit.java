package com.study.zyh.javase.java_genericity.generic_array;

import java.lang.reflect.Array;

/**
 * @ClassName Fruit
 * @Description 创建泛型数组的方式二：通过Array.newInstance()方法
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class Fruit<T> {
    // private T[] array = new T[3];// 直接赋值new T[3]报错
    private T[] array;

    public Fruit(Class<T> clz, int len) {
        array = (T[]) Array.newInstance(clz, len);
    }

    /**
     * 填充数组
     *
     * @param index
     * @param t
     */
    public void put(int index, T t) {
        array[index] = t;
    }

    /**
     * 获取数组元素
     *
     * @param index
     * @return
     */
    public T get(int index) {
        return array[index];
    }

    /**
     * 获取数组
     *
     * @return
     */
    public T[] getArray() {
        return array;
    }
}
