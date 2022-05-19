package com.study.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;
import com.study.rabbitmq.utils.SleepUtil;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName Work02_1
 * @Description 消息在手动应答时不丢失，放回队列中重新消费
 * @Author Zhangyuhan
 * @Date 2022/5/18
 * @Version 1.0
 */
public class Work02_1 {
    // 队列名
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1等待接收消息处理，处理时间较短");

        // 设置不公平分发(默认是0)，也叫设置预取值
        // 关于：basicQos
        /*csdn博客：默认情况下，RabbitMq收到消息后，就向消费者全部推送。但是如果rabbitmq队列里消息过多，且消息的数量超过了消费者处理能力,
        就会导致客户端超负荷崩溃。
        此时我们可以通过 prefetchCount 限制每个消费者在收到下一个确认回执前一次可以最大接受多少条消息。
        即如果设置prefetchCount =1，RabbitMQ向这个消费者发送一个消息后，再这个消息的消费者对这个消息进行ack之前，R
        abbitMQ不会向这个消费者发送新的消息
        原文链接：https://blog.csdn.net/xcl13014673050/article/details/115697911*/
        // 我自己的理解，可以干活的坑位。
        // 设置为1就有1个干活的坑位，设置为2就有2个干活的坑位。。。
        // 收到一个消息，占据一个坑位，应答之后，坑位释放
        // 在坑位满的情况下不能再接收消息
        // 我用书面语概括就是每个信道允许的最多未应答的消息的数量，达到该数量，该信道就不再接收消息
        // channel.basicQos(1);// 只有一个坑位，满了就不能接收消息了
        channel.basicQos(2);// 有两个坑位，两个坑位都满了就不能接收消息了
        // 采用手动应答
        channel.basicConsume(TASK_QUEUE_NAME, false, (consumerTag, message) -> {
            // 沉睡1s种，模拟处理场景
            SleepUtil.sleep(1);
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
