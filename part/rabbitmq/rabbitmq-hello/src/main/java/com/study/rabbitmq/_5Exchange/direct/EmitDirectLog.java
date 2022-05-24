package com.study.rabbitmq._5Exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

import java.util.Scanner;

public class EmitDirectLog {
    // 交换机的名称
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            // 第二个参数是routingKey
            channel.basicPublish(EXCHANGE_NAME, "info", null, message.getBytes());
            channel.basicPublish(EXCHANGE_NAME, "warning", null, message.getBytes());
            channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes());
            System.out.println("生产者发出消息：" + message);
        }
    }
}
