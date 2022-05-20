package com.study.rabbitmq._4pubulish_confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.study.rabbitmq.utils.RabbitMqUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * 验证发布确认模式：
 * 1.单个确认
 * 2.批量确认
 * 3.异步确认
 * 我们需要研究三种确认模式的花费时间，
 */
public class ConfirmMessage {
    // 批量发消息的个数
    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
        // 1.单个确认，确定：速度最慢
//        publishMessageIndividually();// 发布1000个单独确认消息花费804毫秒
        // 2.批量确认,缺点：如果出现消息未确认，无法判断某批消息中的哪个没有确认
//        publishMessageBatch();// 发布1000个批量确认消息花费77毫秒
        // 3.异步确认
        publishMessageAsync();// 发布1000个异步确认消息花费47毫秒
    }

    /**
     * 单个确认
     */
    public static void publishMessageIndividually() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 开启发布确认
        channel.confirmSelect();
        // 声明队列名称
        String queue_name = UUID.randomUUID().toString();
        // 声明队列（队列名，持久化，共享，自动删除，参数）
        channel.queueDeclare(UUID.randomUUID().toString(), true, false, false, null);
        // 开始时间
        long start = System.currentTimeMillis();
        // 批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = String.valueOf(i);
            channel.basicPublish("", queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            // 单个消息发布确认
            boolean publish_res = channel.waitForConfirms();
            // 打印确认结果
            if (publish_res) {
                System.out.println("消息返送成功");
            }
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("发布" + MESSAGE_COUNT + "个单独确认消息花费" + time + "毫秒");
    }

    /**
     * 批量发布确认
     */
    public static void publishMessageBatch() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 开启发布确认
        channel.confirmSelect();
        // 声明队列名称
        String queue_name = UUID.randomUUID().toString();
        // 声明队列（队列名，持久化，共享，自动删除，参数）
        channel.queueDeclare(UUID.randomUUID().toString(), true, false, false, null);
        // 开始时间
        long start = System.currentTimeMillis();
        // 批量发消息
        // 批量确认的消息数量
        int batchSize = 100;
        for (int i = 1; i <= MESSAGE_COUNT; i++) {
            String message = String.valueOf(i);
            channel.basicPublish("", queue_name, null, message.getBytes());

            // 发布消息达到batchSize时，批量确认一次
            if (i % batchSize == 0) {
                boolean publish_res = channel.waitForConfirms();
                if (publish_res) {
                    System.out.println("消息发送成功");
                }
            }
        }

        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("发布" + MESSAGE_COUNT + "个批量确认消息花费" + time + "毫秒");
    }

    // 异步发布确认
    public static void publishMessageAsync() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 开启发布确认
        channel.confirmSelect();
        // 声明队列名称
        String queue_name = UUID.randomUUID().toString();
        // 声明队列（队列名，持久化，共享，自动删除，参数）
        channel.queueDeclare(UUID.randomUUID().toString(), true, false, false, null);
        // 开始时间
        long start = System.currentTimeMillis();

        // 准备消息的监听器，一定要在开始发布消息的前面，监听哪些消息成功，哪些消息失败了
        // addConfirmListener()参数解析：
        // 1.成功回调，监听哪些消息发布成功了
        // 2.失败回调，监听哪些消息发布失败了
        // 回调函数参数解析：
        // 1.消息的标记
        // 2.是否为批量确认
        channel.addConfirmListener((deliveryTag, multiple) -> {
            // 消息发布成功的回调
            System.out.println("确认发布的消息：" + deliveryTag);

        }, (deliveryTag, multiple) -> {
            // 没有应答，消息发布失败的回调
            System.out.println("未确认发布的消息：" + deliveryTag);

        });


        // 批量发布消息(异步)
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = String.valueOf(i);
            channel.basicPublish("", queue_name, null, message.getBytes());
        }


        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("发布" + MESSAGE_COUNT + "个异步确认消息花费" + time + "毫秒");
    }
}