package com.study.rabbitmqspringboot.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 注意：这个接口一定要注册为spring的组件
 */
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback {
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * Constructor >> @Autowired >> @PostConstruct
     * PostConstruct注解能对对依赖注入的bean初始化
     * 将当前接口设置为rabbitTemplate的回调接口
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 交换机确认回调的方法
     * 1.发消息，交换机接收到了消息，回调
     * 2.发消息，交换机接收消息失败（没有接收到消息），回调
     *
     * @param correlationData 回调发送消息的的ID即相关信息
     * @param ack             交换机是否收到消息
     * @param cause           交换机没有收到消息:失败原因；交换机成功收到消息：null
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // 获取消息的id
        String messageId = correlationData == null ? "" : correlationData.getId();
        if (ack) {
            log.info("交换机已经收到ID为{}的消息", messageId);
        } else {
            log.info("交换机没有收到ID为{}的消息，由于原因：{}", messageId, cause);
        }
    }
}
