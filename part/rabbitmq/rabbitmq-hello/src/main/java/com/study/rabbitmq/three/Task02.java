package com.study.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
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
        // 开启队列持久化
        boolean duralbe = true;// 队列是否需要持久化（注意，如果参数改变，需要删除原队列，再重新创建新的队列，否则启动报错）
        channel.queueDeclare(TASK_QUEUE_NAME, duralbe, false, false, null);
        // 从控制台输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            // 生产者发送的消息是非持久化的。（消息保存在内存中）
            // channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            // 设置生产者发送的消息为持久化消息（要求消息保存到磁盘上）
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息：" + message);

        }
    }
}
