package com.study.rabbitmq._6dead_message;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.study.rabbitmq.utils.RabbitMqUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列：实站.消费者1
 */
public class Consumer01 {
    // 普通的交换机的名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    // 死信交换机的名称
    public static final String DEAD_EXCHANGE = "dead_exchange";
    // 普通队列的名称
    public static final String NORMAL_QUEUE = "normal_queue";
    // 死信队列的名称
    public static final String DEAD_QUEUE = "dead_queue";
    // 普通routingKey
    public static final String NORMAL_ROUTING_KEY = "zhangsan";
    // 死信routingKey
    public static final String DEAD_ROUTING_KEY = "lisi";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        // 声明普通交换机和死信交换机，类型是direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明普通队列，注意，有些特别的的参数
        Map<String, Object> arguments = new HashMap<>();
        // 正常的队列设置死信之后的交换机是谁
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 设置死信routingKey
        arguments.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);
        // 设置message的TTL（过期时间）为10000ms（10s）
        // 注意message的过期时间既可以在声明队列处设置，也可以在生产者发送消息的时候针对每条要发送的消息单独设置。后者较为常见
        // arguments.put("x-message-ttl", 10000);
        // 设置队列的最大长度。这里以6为例，当队列里的消息达到6个时，再向队列里发的消息都是死信消息
        arguments.put("x-max-length", 6);
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);

        // 声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        // 绑定交换机与key
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, NORMAL_ROUTING_KEY);
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, DEAD_ROUTING_KEY);


        System.out.println("c1等待接收消息....");

        // 如果时自动应答，那么不存在通过拒绝将消息转为死信的情况，所以这里改为手动应答
        channel.basicConsume(NORMAL_QUEUE, false, (consumerTag, message) -> {
            String msg = new String(message.getBody());
            if ("info5".equals(msg)) {
                System.out.println("info5是被c1拒绝的");
                // 参数解析
                // 1.消息标签
                // 2.是否重新入队
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.println("c1接收的消息是：" + msg);
                // 第二个参数和上面不一样，false表示不批量应答
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        }, (consumerTag) -> {

        });

    }
}
