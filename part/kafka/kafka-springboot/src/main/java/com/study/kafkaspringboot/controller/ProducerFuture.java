package com.study.kafkaspringboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import javax.annotation.Resource;

@Component
@Slf4j
//该类需要为多列类型
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProducerFuture implements FailureCallback, SuccessCallback<SendResult<String, Object>> {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;
    private String uniqueId;

    /**
     * 异步发送消息
     *
     * @param topicLcs
     * @param value
     */
    public void async(String topicLcs, String value) {
        uniqueId = MDC.get("UNIQUE_ID");
        log.info("\nsendTopic {} \ndata:{}", topicLcs, value);
        ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send(topicLcs, value);
        listenableFuture.addCallback(this, this);
    }


    @Override
    public void onFailure(Throwable ex) {
//        MDC.put(UNIQUE_ID, uniqueId);
        //重试策略失败后，将进入该方法。在该重试方法中，可以做其他响应的业务逻辑，如告警、投放其他队列或落地失败的消息内容等
        log.error("sendFailure:", ex);
//        MDC.remove(UNIQUE_ID);
    }

    @Override
    public void onSuccess(SendResult<String, Object> result) {
//        MDC.put(UNIQUE_ID, uniqueId);
        log.info("sendSuccess {} ", result.getRecordMetadata().topic() + result.getRecordMetadata().offset());
//        MDC.remove(UNIQUE_ID);
    }
}
