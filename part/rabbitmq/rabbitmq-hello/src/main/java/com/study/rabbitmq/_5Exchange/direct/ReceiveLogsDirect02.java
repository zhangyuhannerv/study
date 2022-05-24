package com.study.rabbitmq._5Exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

public class ReceiveLogsDirect02 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明一个自动删除的队列
        channel.queueDeclare("disk", false, false, true, null);
        // 绑定交换机与队列(队列名，交换机名称，routingkey)
        channel.queueBind("disk", EXCHANGE_NAME, "error");
        System.out.println("ReceiveLogsDirect02等待接收消息，把接收到的消息打印在屏幕上...");
        channel.basicConsume("disk", true, (consumerTag, message) -> {
            System.out.println("ReceiveLogsDirect02接收到的消息是：" + new String(message.getBody()));
        }, (consumerTag) -> {

        });
    }
}
