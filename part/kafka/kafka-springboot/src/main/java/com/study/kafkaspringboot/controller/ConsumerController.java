package com.study.kafkaspringboot.controller;

import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class ConsumerController {

    @KafkaListener(topics = "first")
    public void consumerTopic(String msg) {
        log.info("收到消息：" + msg);
    }

    @KafkaListener(topics = "tktest")
    public void tktest(String msg) {
        log.info("receiveMsg：" + msg);
    }
}
