package com.study.zyh.javase.java_genericity.generic_array;

import java.util.Arrays;

/**
 * @ClassName FruitTest
 * @Description 测试创建泛型数组的方式二是否正确
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class FruitTest {
    public static void main(String[] args) {
        // new Fruit<String>(Integer.class, 4);// 报错
        Fruit<String> fruit = new Fruit<>(String.class, 5);
        // fruit.put(0, 2);// 报错
        fruit.put(0, "苹果");
        fruit.put(1, "西瓜");
        fruit.put(2, "香蕉");
        System.out.println(fruit.get(0));
        System.out.println(fruit.get(1));
        System.out.println(fruit.get(2));
        System.out.println(Arrays.toString(fruit.getArray()));

        // 关于泛型数组
        // 尽量不要采用泛型数组，真正开发过程中要用泛型集合去代替泛型数组
    }
}
