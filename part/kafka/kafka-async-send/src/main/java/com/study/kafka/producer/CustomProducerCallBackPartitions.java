package com.study.kafka.producer;

import com.study.kafka.config.MyPartitioner;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 异步发送带回调,测试分区算法
 */
public class CustomProducerCallBackPartitions {
    public static void main(String[] args) {
        // 0.配置
        Properties properties = new Properties();
        // 连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 指定对应的key和value的序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 关联自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());
        // 1.创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        // 2.发送数据
        String msg;
        for (int i = 0; i < 5; i++) {
            msg = i % 2 == 0 ? "nmsl" : "wmms";
            kafkaProducer.send(new ProducerRecord<>("first", "a", msg), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e == null) {
                        System.out.println("主题：" + metadata.topic() + "  分区：" + metadata.partition());
                    }
                }
            });
        }

        // 3.关闭资源
        kafkaProducer.close();
    }
}
