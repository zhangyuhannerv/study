package com.study.rabbitmq._6dead_message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

/**
 * 消息生产者
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 发送死信信息。之所有称之为死信消息，是因为设置了TTL,这里以10s为例子
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 0; i < 10; i++) {
            String message = "info" + i;
            channel.basicPublish(Consumer01.NORMAL_EXCHANGE, Consumer01.NORMAL_ROUTING_KEY, basicProperties, message.getBytes());
        }
    }
}
