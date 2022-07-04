package com.study.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    // 当支付服务为集群时，使用负载均衡，默认使用ribbon的轮询策略。当支付服务是单机时，不用加该注解。
    // 虽然像上面那么说，但是不加该注解好像无论单机还是集群都报错。所以还是都加上吧
    // 当使用自己手写的负载均衡算法时，注释掉该注解。让自带的lb不生效
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
