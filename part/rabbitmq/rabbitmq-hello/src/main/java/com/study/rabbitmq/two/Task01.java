package com.study.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

import java.util.Scanner;

/**
 * @ClassName Task01
 * @Description 生产者，可以发送大量的消息（本案例中是从控制台输入）
 * @Author Zhangyuhan
 * @Date 2022/5/17
 * @Version 1.0
 */
public class Task01 {
    private static final String QUEUE_NAME = "hello";

    // 发送大量的消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 队列的声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 从控制台当中接收消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("发送消息完成：" + message);
        }
    }
}
