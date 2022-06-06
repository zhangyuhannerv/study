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

    // 备份的交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";

    // 备份的队列
    public static final String BACKUP_QUEUE_NAME = "backup_queue";

    // 报警队列
    public static final String WARNING_QUEUE_NAME = "warning_queue";

    // 声明确认交换机
    @Bean
    public DirectExchange confirmExchange() {
        return ExchangeBuilder
                .directExchange(CONFIRM_EXCHANGE_NAME)
                .alternate(BACKUP_EXCHANGE_NAME)// 这里声明备份交换机
                .build();
    }


    // 备份交换机(扇出类型）
    @Bean
    public FanoutExchange backupExchange() {
        return ExchangeBuilder.fanoutExchange(BACKUP_EXCHANGE_NAME).build();
    }


    // 声明队列
    @Bean
    public Queue confirmQueue() {
        return QueueBuilder.nonDurable(CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Queue backupQueue() {
        return QueueBuilder.nonDurable(BACKUP_QUEUE_NAME).build();
    }

    @Bean
    public Queue warningQueue() {
        return QueueBuilder.nonDurable(WARNING_QUEUE_NAME).build();
    }

    // 绑定
    @Bean
    public Binding confirmQueueBindConfirmExchange(Queue confirmQueue, DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    public Binding backupQueueBindBackupExchange(Queue backupQueue, FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }


    @Bean
    public Binding warningQueueBindConfirmExchange(Queue warningQueue, FanoutExchange backupExchange) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }


}
