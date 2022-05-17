package com.study.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

/**
 * @ClassName Work01
 * @Description 这是一个工作线程，相当于之前one里的consumer
 * @Author Zhangyuhan
 * @Date 2022/5/17
 * @Version 1.0
 */
public class Worker01 {
    private static final String QUEUE_NAME = "hello";

    // 接收消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        // 消息
        channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("接收到的消息：" + new String(message.getBody()));
        }, (consumerTag) -> {
            System.out.println(consumerTag + "消费者取消消费的接口回调逻辑");
        });
    }
}
