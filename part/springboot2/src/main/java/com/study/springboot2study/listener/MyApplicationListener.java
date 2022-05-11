package com.study.springboot2study.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @ClassName MyApplicationListerer
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/12
 * @Version 1.0
 */
public class MyApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("MyApplicationListener...onApplicationEvent...");
    }
}
