package com.study.kafkaspringboot.controller;

import com.study.kafkaspringboot.model.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProducerController {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private ProducerFuture producerFuture;

    @RequestMapping("/sendMsg")
    public String sendMsg(String msg) {
        kafkaTemplate.send("first", msg);
        return "ok";
    }

    @RequestMapping("/sendMsg1")
    public void sendMsg1() {
        Message message = new Message();
        message.setData("{\n" +
                "\"cg\":\"-8.18\",\"czjsd\":\"0.0173\",\"dataDescription\":\"GZ22X-QIDIAN-ZHONGDIAN-08072021-152155-0.txt\",\"dmbz\":\"-20\",\"gj\":\"-1.44\",\"gjbhl\":\"-0.78\",\"gl\":\"446\",\"hxjsd\":\"-0.0056\",\"id\":\"1\",\"jcrq\":\"08072021:152155\",\"lc\":\"446549.75\",\"ming\":\"549.75\",\"ql\":\"0.0\",\"sd\":\"65\",\"sjk\":\"1.45\",\"sp\":\"0.65\",\"xb\":\"X\",\"xl\":\"22\",\"ygd\":\"0.69\",\"ygd_70\":\"0.95\",\"ygx\":\"-0.08\",\"ygx_70\":\"-1.07\",\"zgd\":\"0.77\",\"zgd_70\":\"2.06\",\"zgx\":\"-0.61\",\"zgx_70\":\"-1.14\"，\"lrsj\":\"1629940398442\"\n" +
                "}");
        message.setType("BMS_VCU_1613");
        producerFuture.async("tktest", message.getData().toString());
    }
}
