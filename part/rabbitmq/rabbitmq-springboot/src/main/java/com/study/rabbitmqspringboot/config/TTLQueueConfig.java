package com.study.rabbitmqspringboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * TTL队列，配置文件类
 */
@Configuration
public class TTLQueueConfig {
    // 普通交换机
    public static final String X_EXCHANGE = "X";
    // 死信交换机
    public static final String DEAD_LETTER_EXCHANGE_Y = "Y";
    // 普通队列1
    public static final String QUEUE_A = "QA";
    // 普通队列2
    public static final String QUEUE_B = "QB";
    // 死信队列
    public static final String DEAD_LETTER_QUEUE_D = "QD";

    // 声明普通交换机
    @Bean()
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    // 声明死信交换机
    @Bean()
    public DirectExchange yExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE_Y);
    }

    // 声明普通队列,有过期时间
    @Bean
    public Queue queueA() {
        Map<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_Y);
        // 设置死信routingKey
        arguments.put("x-dead-routing-key", "YD");
        // 设置过期时间(10s)
        arguments.put("x-message-ttl", 10000);
        return QueueBuilder.nonDurable(QUEUE_A).withArguments(arguments).build();
    }

    @Bean
    public Queue queueB() {
        Map<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_Y);
        // 设置死信routingKey
        arguments.put("x-dead-routing-key", "YD");
        // 设置过期时间(10s)
        arguments.put("x-message-ttl", 40000);
        return QueueBuilder.nonDurable(QUEUE_B).withArguments(arguments).build();
    }

    // 声明死信队列
    @Bean
    public Queue queueD() {
        return QueueBuilder.nonDurable(DEAD_LETTER_QUEUE_D).build();
    }

    // 绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queue, @Qualifier("xExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("XA");
    }

    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queue, @Qualifier("xExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("XB");
    }

    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queue, @Qualifier("yExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("YD");
    }
}
