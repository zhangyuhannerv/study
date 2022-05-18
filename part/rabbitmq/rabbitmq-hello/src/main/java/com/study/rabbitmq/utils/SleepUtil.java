package com.study.rabbitmq.utils;

/**
 * @ClassName SleepUtil
 * @Description 线程睡眠工具类
 * @Author Zhangyuhan
 * @Date 2022/5/18
 * @Version 1.0
 */
public class SleepUtil {
    public static void sleep(int second) {
        try {
            Thread.sleep(1000L * second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
