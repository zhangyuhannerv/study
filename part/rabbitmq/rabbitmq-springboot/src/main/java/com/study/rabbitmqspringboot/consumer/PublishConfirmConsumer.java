package com.study.rabbitmqspringboot.consumer;

import com.study.rabbitmqspringboot.config.AdvancedPublishConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PublishConfirmConsumer {
    @RabbitListener(queues = AdvancedPublishConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmMessage(Message message) {
        String msg = new String(message.getBody());
        log.info("接收到队列{}消息：{}", AdvancedPublishConfirmConfig.CONFIRM_QUEUE_NAME, msg);
    }

}
