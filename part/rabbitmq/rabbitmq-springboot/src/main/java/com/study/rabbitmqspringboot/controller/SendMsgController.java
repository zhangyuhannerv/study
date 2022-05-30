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
     * 发送消息 ttl基于队列
     * 问题：不同的延时时间需要建立不同的队列
     */
    @GetMapping("/sendMsg/{msg}")
    public void sendMsg(@PathVariable("msg") String msg) {
        log.info("当前时间：{}，发送一条消息给两个队列：{}", new Date(), msg);
        rabbitTemplate.convertAndSend("normal_exchange", "normal_routing_key_a", "延时10秒的队列a" + msg);
        rabbitTemplate.convertAndSend("normal_exchange", "normal_routing_key_b", "延时40秒的队列a" + msg);
    }

    /**
     * 发送消息，ttl基于生产者
     * 测试：
     * /你好1/20000
     * /你好2/2000
     * 发现巨大的问题：如果第一条延时消息很长，第二条延时消息很短，那么第二条并不会优先进入死信队列，因为rabbitMq只会检查第一条消息是否过期
     */
    @GetMapping("/sendExpirationMsg/{msg}/{ttl}")
    public void sendExpirationMsg(@PathVariable("msg") String msg, @PathVariable("ttl") String ttl) {
        log.info("当前时间：{}，发送一条ttl是{}毫秒的消息给队列c：{}", new Date(), ttl, msg);
        rabbitTemplate.convertAndSend("normal_exchange", "normal_routing_key_c", msg, message -> {
            // 设置发送消息的时候参数
            // 这里只需设置过期时间
            message.getMessageProperties().setExpiration(ttl);
            return message;
        });
    }
}
