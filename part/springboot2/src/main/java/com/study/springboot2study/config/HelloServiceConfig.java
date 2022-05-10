package com.study.springboot2study.config;

import com.study.springbootstarterhelloautoconfigure.hello.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName HelloServiceConfig
 * @Description 如果在这里自定义了一个HelloService的bean, 那么就不走自动配置类里面的helloService了
 * @Author Zhangyuhan
 * @Date 2022/5/10
 * @Version 1.0
 */
// @Configuration
public class HelloServiceConfig {
    @Bean
    public HelloService helloService() {
        return new HelloService();
    }
}
