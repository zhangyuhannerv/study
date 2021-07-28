package com.zh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName com.zh.DemoApplication
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/7/27
 * @Version 1.0
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class DemoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}