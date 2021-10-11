package com.zh.work_demo;

import java.util.Optional;

/**
 * @ClassName _3消除parseInt类型转换异常
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/10/9
 * @Version 1.0
 */
public class _3消除parseInt类型转换异常 {

    public static Optional<Integer> StringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        System.out.println(StringToInt("12.0"));
    }

}
