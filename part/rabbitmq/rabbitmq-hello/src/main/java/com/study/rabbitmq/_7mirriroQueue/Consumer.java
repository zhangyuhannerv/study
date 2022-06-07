package com.study.rabbitmq._7mirriroQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Consumer
 * @Description 消费者代码，接收消息
 * @Author Zhangyuhan
 * @Date 2022/5/16
 * @Version 1.0
 */
public class Consumer {
    // 队列名称
    public static final String QUEUE_NAME = "mirrior_hello";

    // 接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置工厂的连接地址
        factory.setHost("192.168.220.22");
        //设置用户名
        factory.setUsername("admin");
        // 设置密码
        factory.setPassword("123");

        // 创建连接
        Connection connection = factory.newConnection();
        // 获取信道（channel）
        Channel channel = connection.createChannel();

        // 消费者消费消息,参数说明
        // 1.消费哪个队列
        // 2.消费成功之后是否要自动应答，true代表自动应答，false代表手动应答
        // 3.消费者成功消费的回调（成功接收消息的回调函数）
        // 4.消费者取消消费的回调（接收不到消息的回调函数）
        channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
            // message有消息头，消息属性，消息体
            System.out.println(new String(message.getBody()));
        }, (consumerTag) -> {
            System.out.println("消息消费被中断");
        });
    }
}
