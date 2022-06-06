package com.study.rabbitmqspringboot.controller;

import com.study.rabbitmqspringboot.config.AdvancedPublishConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/confirm")
@Slf4j
public class ProducerController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发消息，测试高级发布确认
     */
    @GetMapping("/sendConfirmMessage/{message}")
    public void sendConfirmMessage(@PathVariable String message) {
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(
                AdvancedPublishConfirmConfig.CONFIRM_EXCHANGE_NAME,
                AdvancedPublishConfirmConfig.CONFIRM_ROUTING_KEY,
                message,
                correlationData);
        // 故意写错交换机的名字，测试交换机收不到消息触发回调
        // 结果成功触发交换机收消息失败回调
        rabbitTemplate.convertAndSend(
                AdvancedPublishConfirmConfig.CONFIRM_EXCHANGE_NAME + "123",
                AdvancedPublishConfirmConfig.CONFIRM_ROUTING_KEY,
                message,
                correlationData);
        // 故意写错routingKey,测试交换机收到消息，队列收不到消息的情况
        // 结果触发的是交换机成功收到消息的回调
        rabbitTemplate.convertAndSend(
                AdvancedPublishConfirmConfig.CONFIRM_EXCHANGE_NAME,
                AdvancedPublishConfirmConfig.CONFIRM_ROUTING_KEY + "123",
                message,
                correlationData);
        log.info("发送消息内容为：{}", message);
    }
}
