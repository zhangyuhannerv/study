package com.study.rabbitmqspringboot.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TTLQueueConfig {
    /**
     * 普通交换机
     */
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    /**
     * 死信交换机
     */
    public static final String DEAD_EXCHANGE = "dead_exchange";
    /**
     * 普通队列a
     */
    public static final String NORMAL_QUEUE_A = "normal_queue_a";
    /**
     * 普通队列b
     */
    public static final String NORMAL_QUEUE_B = "normal_queue_b";

    /**
     * 死信队列
     */
    public static final String DEAD_QUEUE = "dead_queue";
    /**
     * 普通队列a路由key
     */
    public static final String NORMAL_ROUTING_KEY_A = "normal_routing_key_a";
    /**
     * 普通队列b路由key
     */
    public static final String NORMAL_ROUTING_KEY_B = "normal_routing_key_b";
    /**
     * 死信队列路由key
     */
    public static final String DEAD_ROUTING_KEY = "dead_routing_key";

    /**
     * 声明普通交换机
     * 使用直接交换机 发布订阅方式
     * 默认持久化 消费者断开连接时不自动删除
     */
    @Bean
    public DirectExchange normalExchange() {
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    /**
     * 声明死信交换机
     */
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE);
    }

    /**
     * 声明普通队列a
     * 设置ttl时间为10秒
     */
    @Bean
    public Queue queueA() {
        return QueueBuilder.nonDurable(NORMAL_QUEUE_A).deadLetterExchange(DEAD_EXCHANGE).deadLetterRoutingKey(DEAD_ROUTING_KEY).ttl(10000).build();
    }

    /**
     * 声明普通队列b
     * 设置ttl时间为40秒
     */
    @Bean
    public Queue queueB() {
        return QueueBuilder.nonDurable(NORMAL_QUEUE_B).deadLetterExchange(DEAD_EXCHANGE).deadLetterRoutingKey(DEAD_ROUTING_KEY).ttl(40000).build();
    }


    /**
     * 声明死信队列
     */
    @Bean
    public Queue queueD() {
        return QueueBuilder.nonDurable(DEAD_QUEUE).build();
    }

    /**
     * 声明普通队列a与普通交换机绑定 设置路由key
     * 参数因为已经注册在容器中 会自动从容器中取 但是名字必须一样
     */
    @Bean
    public Binding queueBindingA(Queue queueA, DirectExchange normalExchange) {
        return BindingBuilder.bind(queueA).to(normalExchange).with(NORMAL_ROUTING_KEY_A);
    }

    /**
     * 声明普通队列b与普通交换机绑定 设置路由key
     */
    @Bean
    public Binding queueBindingB(Queue queueB, DirectExchange normalExchange) {
        return BindingBuilder.bind(queueB).to(normalExchange).with(NORMAL_ROUTING_KEY_B);
    }

    /**
     * 声明死信队列与死信交换机绑定
     */
    @Bean
    public Binding queueBindingD(Queue queueD, DirectExchange deadExchange) {
        return BindingBuilder.bind(queueD).to(deadExchange).with(DEAD_ROUTING_KEY);
    }

}

