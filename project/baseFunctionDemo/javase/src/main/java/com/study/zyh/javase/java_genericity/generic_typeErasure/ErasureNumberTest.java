package com.study.zyh.javase.java_genericity.generic_typeErasure;

import java.lang.reflect.Field;

/**
 * @ClassName ErasureNumberTest
 * @Description 演示有限制的类型擦除
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class ErasureNumberTest {
    public static void main(String[] args) {
        ErasureNumber<Integer> erasureNumber = new ErasureNumber<>();
        Class<? extends ErasureNumber> clz = erasureNumber.getClass();

        // 通过反射来获取erasure类所有的成员变量。看看字节码文件里T是什么？
        Field[] declaredFields = clz.getDeclaredFields();// 获取所有的成员变量
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName() + ":" + declaredField.getType().getSimpleName());// 打印字段名字和字段类型的短名
        }
        // 结论：在编译结束后(字节码文件里)，key已经从Integer变成了Number类型
        // 这是一种有限制的类型擦除，直接将Integer变成Number
    }
}
