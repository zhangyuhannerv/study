package com.study.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced// 当支付服务为集群时，指定负载均衡策略是轮询。当支付服务是单机时，不用加该注解。
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
