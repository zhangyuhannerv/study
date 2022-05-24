package com.study.rabbitmq._5Exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

public class ReceiveLogsTopic01 {
    public static final String EXCHANGE_NAME = "topic_logs";
    public static final String QUEUE_NAME = "Q1";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 生命交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        // 队列绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*.orange.*");

        System.out.println("等待接收消息");
        // 消费
        channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("收到的消息：" + new String(message.getBody()) + ",绑定的键是" + message.getEnvelope().getRoutingKey());
        }, (consumerTag) -> {

        });
    }
}
