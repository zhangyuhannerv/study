package com.zh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
@MapperScan("com.zh.**.mapper")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
