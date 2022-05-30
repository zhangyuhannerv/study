package com.study.rabbitmqspringboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 普通发送消息 基于死信队列
     */
    @GetMapping("/sendMsg/{msg}")
    public void sendMsg(@PathVariable("msg") String msg) {
        log.info("当前时间：{}，发送一条消息给两个队列：{}", new Date(), msg);
        rabbitTemplate.convertAndSend("normal_exchange", "normal_routing_key_a", "延时10秒的队列a" + msg);
        rabbitTemplate.convertAndSend("normal_exchange", "normal_routing_key_b", "延时40秒的队列a" + msg);
    }
}
