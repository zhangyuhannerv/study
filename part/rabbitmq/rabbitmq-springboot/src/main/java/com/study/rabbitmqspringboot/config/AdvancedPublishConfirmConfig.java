package com.study.rabbitmqspringboot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类：发布确认高级
 */
@Configuration
public class AdvancedPublishConfirmConfig {
    // 交换机名称
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    // 队列名称
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    // routingKey
    public static final String CONFIRM_ROUTING_KEY = "confirm_key";

    // 声明交换机
    @Bean
    public DirectExchange confirmExchange() {
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).build();
    }


    // 声明队列
    @Bean
    public Queue confirmQueue() {
        return QueueBuilder.nonDurable(CONFIRM_QUEUE_NAME).build();
    }

    // 绑定
    @Bean
    public Binding confirmQueueBindConfirmExchange(Queue confirmQueue, DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

}
