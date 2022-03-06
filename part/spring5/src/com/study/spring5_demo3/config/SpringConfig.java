package com.study.spring5_demo3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SpringConfig
 * @Description 完全注解开发，spring的配置类
 * @Author Zhangyuhan
 * @Date 2022/3/4
 * @Version 1.0
 */


@Configuration// 配置类，替代xml配置文件

// 该注解代替了<context:component-scan base-package="com.study.spring5_demo3"/>
@ComponentScan(basePackages = {"com.study.spring5_demo3"})
public class SpringConfig {

}
