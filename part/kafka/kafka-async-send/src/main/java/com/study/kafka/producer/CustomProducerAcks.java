package com.study.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 发送消息至kafka broker的应答策略
 */
public class CustomProducerAcks {
    public static void main(String[] args) {
        // 0.配置
        Properties properties = new Properties();
        // 连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 指定对应的key和value的序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // acks
        properties.put(ProducerConfig.ACKS_CONFIG, "1");// 默认是-1
        // 重试次数(默认是int的最大值）
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);// 收不到应答的重试次数：3


        // 1.创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        // 2.发送数据
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord<>("first", "hello" + i));
        }

        // 3.关闭资源
        kafkaProducer.close();
    }
}
