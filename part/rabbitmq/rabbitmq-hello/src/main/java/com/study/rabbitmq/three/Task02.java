package com.study.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @ClassName Task02
 * @Description 消息在手动应答时不丢失，放回队列中重新消费
 * @Author Zhangyuhan
 * @Date 2022/5/18
 * @Version 1.0
 */
public class Task02 {
    // 队列名
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

        // 从控制台输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息：" + message);

        }
    }
}
