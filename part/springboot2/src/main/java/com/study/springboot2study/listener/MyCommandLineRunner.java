package com.study.springboot2study.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyCommandLineRunner
 * @Description 常用于应用启动做一个一次性事情
 * @Author Zhangyuhan
 * @Date 2022/5/12
 * @Version 1.0
 */

@Order(2)
@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("MyCommandLineRunner...run...");
    }
}
