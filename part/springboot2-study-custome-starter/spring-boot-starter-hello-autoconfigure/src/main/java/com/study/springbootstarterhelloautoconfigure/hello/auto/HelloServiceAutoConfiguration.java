package com.study.springbootstarterhelloautoconfigure.hello.auto;

import com.study.springbootstarterhelloautoconfigure.hello.bean.HelloProperties;
import com.study.springbootstarterhelloautoconfigure.hello.service.HelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName HelloServiceAutoConfigure
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/10
 * @Version 1.0
 */

@Configuration
// @EnableConfigurationProperties两个作用：
// 1.HelloProperties放在IOC容器中
// 2.HelloProperties和配置文件进行绑定
@EnableConfigurationProperties(HelloProperties.class)
public class HelloServiceAutoConfiguration {

    @ConditionalOnMissingBean(HelloService.class)
    @Bean
    public HelloService helloService() {
        HelloService helloService = new HelloService();
        return helloService;
    }
}
