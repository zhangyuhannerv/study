package com.study.rabbitmq._5Exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

public class ReceiveLogsDirect01 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明一个自动删除的队列
        channel.queueDeclare("console", false, false, true, null);
        // 绑定交换机与队列(队列名，交换机名称，routingKey)
        // 同一个队列和一个交换机可以通过不同的routingKey绑定多次。
        channel.queueBind("console", EXCHANGE_NAME, "info");
        channel.queueBind("console", EXCHANGE_NAME, "warning");
        System.out.println("ReceiveLogsDirect01等待接收消息，把接收到的消息打印在屏幕上...");
        channel.basicConsume("console", true, (consumerTag, message) -> {
            System.out.println("ReceiveLogsDirect01接收到的消息是：" + new String(message.getBody()));
        }, (consumerTag) -> {

        });
    }
}
