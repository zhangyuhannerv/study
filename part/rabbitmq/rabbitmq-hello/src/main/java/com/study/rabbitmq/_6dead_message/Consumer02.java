package com.study.rabbitmq._6dead_message;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

import java.util.Arrays;

public class Consumer02 {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("consumer02等待接收消息");
        channel.basicConsume(Consumer01.DEAD_QUEUE, true, (consumerTag, message) -> {
            System.out.println("consumer02接收到的消息：" + new String(message.getBody()));
        }, (consumerTag) -> {

        });

    }
}
