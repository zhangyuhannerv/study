package com.study.rabbitmqspringboot.consumer;

import com.study.rabbitmqspringboot.config.PriorityQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 注意,不要发送一条消费一条，此时队列无法根据优先级去将消息排序。要把发送的多条消息都放入到队列中，然后才让消费者去消费
// 要实现上面的效果就是先注释掉@Component（无消费者）再启用注解，重启项目，观察控制台打印结果
// @Component
@Slf4j
public class PriorityQueueConsumer {
    @RabbitListener(queues = PriorityQueueConfig.PRIORITY_QUEUE_NAME)
    public void receiveMsg(Message message) {
        log.info("收到优先级队列的消息：{}", new String(message.getBody()));
    }
}
