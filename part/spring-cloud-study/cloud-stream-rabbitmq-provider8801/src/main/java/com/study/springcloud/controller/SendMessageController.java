package com.study.springcloud.controller;

import com.study.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SendMessageController {
    @Autowired
    IMessageProvider messageProvider;


    @GetMapping("/sendMessage")
    public void sendMessage() {
        messageProvider.send();
    }
}
