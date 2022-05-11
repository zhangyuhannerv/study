package com.study.springboot2study.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyApplicationRunner
 * @Description 常用于应用启动做一个一次性事情
 * @Author Zhangyuhan
 * @Date 2022/5/12
 * @Version 1.0
 */

@Order(1)// 优先级较MyCommandLineRunner低
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("MyApplicationRunner...run...");
    }
}
