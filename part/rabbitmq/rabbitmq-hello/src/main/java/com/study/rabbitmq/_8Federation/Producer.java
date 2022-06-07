package com.study.rabbitmq._8Federation;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

/**
 * 创建联邦交换机
 */
public class Producer {
    public static final String FED_EXCHANGE = "fed_exchange";
    public static final String QUEUE_NAME = "node2_queue";
    public static final String ROUTING_KEY = "routeKey";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(FED_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, FED_EXCHANGE, ROUTING_KEY);
    }
}
