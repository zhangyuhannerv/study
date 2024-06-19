package com.study.zyh.javase.java_genericity.generic_array;

import java.util.ArrayList;

/**
 * @ClassName BaseShow
 * @Description 泛型数组的基本演示
 * 创建方式一：可以声明带泛型的数组引用，但是不能直接创建带泛型的数组对象
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class BaseShow {
    public static void main(String[] args) {
        ArrayList[] list = new ArrayList[5];
        // ArrayList<String>[] listArr = new ArrayList<String>[5];// 直接声明泛型数组报错
        ArrayList<String>[] listArr = list;// 由于不能直接声明泛型数组，所以用不是泛型的数组赋值给泛型数组

        // 此时不是泛型的数组里面可以放进去任意值
        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(100);
        list[0] = intList;

        // 从是泛型的数组里取出一个值
        String s = listArr[0].get(0);
        System.out.println(s);// 报错:ClassCastException(Integer->String),这也是上面报警告的原因(不安全)

        // 解决办法
        ArrayList<String>[] listArr1 = new ArrayList[5];// 不要把上面的list暴露出来，直接把匿名对象赋给泛型数组
        // listArr1[0] = intList;// 编译阶段就报错
    }
}
