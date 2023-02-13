package com.itheima.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @param
 * @author Shuwq
 * @Description 启动类
 * @MethodName ConfigStart
 * @date 2019/10/26 22:08
 * @return
 */
@SpringBootApplication(scanBasePackages = {"com.itheima.shiro.interceptor",
        "com.itheima.shiro.service", "com.itheima.shiro.config", "com.itheima.shiro.intialize"})
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class SpringbootShiroConfigStart extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShiroConfigStart.class, args);
    }
}