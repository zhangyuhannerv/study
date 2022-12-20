package com.study.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 提高生成者吞吐量的几个参数的设置
 */
public class CustomProducerParameters {
    public static void main(String[] args) {
        Properties properties = new Properties();
        // 连接kafka集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // key和value的序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 参数1：缓冲区大小
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);// 32m(默认大小）
        // 参数2：批次大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);// 16k（默认大小）
        // 参数3：linger.ms
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 5);// 默认0ms
        // 参数4：压缩方式(mac电脑用了压缩方式后，虽然发送成功，但是命令行无法消费命令）
//        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");// 企业常用snappy,其他还有gzip等等

        // 创建生成者
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        // 发送数据
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord<>("first", "nmsl" + i));
        }
        // 关闭资源
        kafkaProducer.close();
    }
}
