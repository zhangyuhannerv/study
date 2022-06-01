package com.study.rabbitmqspringboot.controller;

import com.study.rabbitmqspringboot.config.AdvancedPublishConfirmConfig;
import lombok.extern.slf4j.Slf4j;
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
        rabbitTemplate.convertAndSend(AdvancedPublishConfirmConfig.CONFIRM_EXCHANGE_NAME, AdvancedPublishConfirmConfig.CONFIRM_ROUTING_KEY, message);
        log.info("发送消息内容为：{}", message);
    }
}
