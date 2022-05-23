package com.study.rabbitmq._5Exchange.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

public class ReceiveLogs02 {
    // 交换机的名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // 声明一个临时队列(队列的名称是随机的）
        // 当消费者断开连接，队列就删除了
        String queueName = channel.queueDeclare().getQueue();
        // 绑定交换机与队列
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("ReceiveLogs02等待接收消息，把接收到的消息打印在屏幕上...");
        channel.basicConsume(queueName, true, (consumerTag, message) -> {
            System.out.println("ReceiveLogs02接收到的消息是：" + new String(message.getBody()));
        }, (consumerTag) -> {

        });
    }
}
