package com.study.rabbitmqspringboot.controller;

import com.study.rabbitmqspringboot.config.PriorityQueueConfig;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送优先级消息
 */
@RestController
public class PriorityProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendPriorityMsg")
    public void sendPriorityMsg() {
        for (int i = 0; i < 10; i++) {
            String msg = "priorityInfo" + i;
            if (i == 5) {
                rabbitTemplate.convertAndSend(PriorityQueueConfig.PRIORITY_QUEUE_NAME, msg, correlationData -> {
                    // 设置消息发送的优先级
                    // 测试结果：info5最先收到
                    correlationData.getMessageProperties().setPriority(5);
                    return correlationData;
                });
            } else {
                // 没有设置优先级的消息优先级最低
                rabbitTemplate.convertAndSend(PriorityQueueConfig.PRIORITY_QUEUE_NAME, msg);
            }

        }
    }
}
