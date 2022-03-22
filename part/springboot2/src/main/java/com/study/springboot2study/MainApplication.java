package com.study.springboot2study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName MainApplication
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/21
 * @Version 1.0
 */

/**
 * 编写主程序类，固定写法
 *
 * @SpringBootApplication 告诉springboot这是一个springboot应用
 */
@SpringBootApplication
// 注意：自动扫描默认是运行类所在的包及其子包
// 如果想扫描别的兄弟包，可以扩大扫描范围,如下
// @SpringBootApplication(scanBasePackages = "com.study")
public class MainApplication {
    public static void main(String[] args) {
        // 返回IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(MainApplication.class, args);
        // 查看容器里的组件
        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }
}
