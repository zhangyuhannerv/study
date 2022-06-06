package com.study.rabbitmqspringboot.consumer;

import com.study.rabbitmqspringboot.config.AdvancedPublishConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 报警消费者
 */
@Component
@Slf4j
public class WarningConsumer {
    // 消息的接收，接收报警消息
    // 注意：备份交换机优先级高于队列消息回退。
    // 即交换机一个消息没有转到队列时，同时设置了消息回退和备份交换机，那么只有备份交换机生效，消息回退回调接口不生效
    // 如果交换机没有收到消息，那么备份交换机不生效，只有交换机确认消息回调接口生效
    @RabbitListener(queues = AdvancedPublishConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.warn("报警：发现不可路由消息：{}", msg);
    }
}
