package com.study.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Description 生产者代码：目标是发消息
 * @Author Zhangyuhan
 * @Date 2022/5/16
 * @Version 1.0
 */
public class Producer {
    // 队列名称
    public static final String QUEUE_NAME = "hello";

    // 发消息
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置工厂的连接地址
        factory.setHost("192.168.220.21");
        //设置用户名
        factory.setUsername("admin");
        // 设置密码
        factory.setPassword("123");

        // 创建连接
        Connection connection = factory.newConnection();
        // 获取信道（channel）
        Channel channel = connection.createChannel();
        // 创建一个队列
        // 1.队列名称
        // 2.队列里面的消息是否需要保存（持久化），默认不持久化（消息存储在内存中）
        // 3.该队列是否只供一个消费者进行消费，是否进行消息的共享，true代表可以多个消费者进行消费，false不允许多个消费着消费（默认是false,只能一个消费者消费）
        // 4.是否自动删除，最后一个消费者端开连接以后，该队列是否自动删除，如果是true自动删除，false为不自动删除
        // 5.Map<String,Object>其他参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 发消息
        String message = "hello world";
        // 发消息的参数说明
        // 1.交换机
        // 2.路由的Key值，本次是队列的名称
        // 3.表示其他参数信息
        // 4.发送消息的消息体
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息发送完毕");
    }
}
