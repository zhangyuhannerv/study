package com.study.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置feign的日志增强，即监控rpc调用的详细信息(它分为四个级别，默认为none。这里选择full，即接口调用过程的全部信息）
 * 注意这里还要配合yml里的日志配置一起使用（详情看yml）
 */
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
