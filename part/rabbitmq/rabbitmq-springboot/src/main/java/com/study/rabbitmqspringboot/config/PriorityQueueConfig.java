package com.study.rabbitmqspringboot.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriorityQueueConfig {
    public static final String PRIORITY_QUEUE_NAME = "priority_queue";

    // 设置队列允许的最大优先级
    // 官方允许0-255
    // 实际生产一般设置允许优先级的范围为0-10，不要设置过大，浪费cpu和内存
    @Bean
    public Queue priorityQueue() {
        return QueueBuilder.nonDurable(PRIORITY_QUEUE_NAME).maxPriority(10).build();
    }
}
