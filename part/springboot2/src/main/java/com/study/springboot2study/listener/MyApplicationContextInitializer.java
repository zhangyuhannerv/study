package com.study.springboot2study.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName MyApplicationContextInitializer
 * @Description 自定义事件监听组件
 * @Author Zhangyuhan
 * @Date 2022/5/12
 * @Version 1.0
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("MyApplicationContextInitializer...");
    }
}
