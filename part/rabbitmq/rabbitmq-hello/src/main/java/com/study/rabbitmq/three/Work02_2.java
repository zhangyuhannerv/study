package com.study.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;
import com.study.rabbitmq.utils.SleepUtil;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName Work02_2
 * @Description 消息在手动应答时不丢失，放回队列中重新消费
 * @Author Zhangyuhan
 * @Date 2022/5/18
 * @Version 1.0
 */
public class Work02_2 {
    // 队列名
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C2等待接收消息处理，处理时间较短");

        // 采用手动应答
        channel.basicConsume(TASK_QUEUE_NAME, false, (consumerTag, message) -> {
            // 沉睡1s种，模拟处理场景
            SleepUtil.sleep(30);
            System.out.println("接收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
            // 手动应答,参数详解
            // 1.消息的标记 tag
            // 2.是否批量应答,这里选择false,不批量应答信道里的消息,即处理一个应答一个
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, (consumerTag) -> {
            System.out.println(consumerTag + "消费者取消消费接口回调");
        });
    }
}
