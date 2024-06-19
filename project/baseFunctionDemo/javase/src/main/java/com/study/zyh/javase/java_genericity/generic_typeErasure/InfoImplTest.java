package com.study.zyh.javase.java_genericity.generic_typeErasure;

import java.lang.reflect.Method;

/**
 * @ClassName InfoImplTest
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class InfoImplTest {
    public static void main(String[] args) {
        Method[] declaredMethods = InfoImpl.class.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName() + ":" + declaredMethod.getReturnType().getSimpleName());
        }

        // 结论：
        // info:Integer
        // info:Object
        // 发现有两个info()方法。一个是保持泛型实现类的方法，返回Integer。一个是联系接口泛型擦除后的方法,返回Object。
        // 这种现象叫做泛型擦除里的桥接(泛型接口的实现类里面有两个方法)
    }
}
