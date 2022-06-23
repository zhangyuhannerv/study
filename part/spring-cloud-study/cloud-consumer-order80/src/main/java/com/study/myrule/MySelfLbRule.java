package com.study.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 替换ribbon默认的轮询负载均衡策略
 */
@Configuration
public class MySelfLbRule {
    @Bean
    public IRule myRule() {
        // 轮询->随机
        return new RandomRule();
    }
}
