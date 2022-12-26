package com.study.kafkaspringboot.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProducerController {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/sendMsg")
    public String data(String msg) {
        kafkaTemplate.send("first", msg);
        return "ok";
    }
}
