package com.study.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 事物（要求幂等性开启，但是幂等性是默认开启的）
 */
public class CustomProducerTransactions {
    public static void main(String[] args) {
        // 0.配置
        Properties properties = new Properties();
        // 连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 指定对应的key和value的序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 指定事务id
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction_id_01");

        // 1.创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        // 初始化事务
        kafkaProducer.initTransactions();
        // 开始事务
        kafkaProducer.beginTransaction();
        try {
            // 2.发送数据
            for (int i = 0; i < 5; i++) {
                kafkaProducer.send(new ProducerRecord<>("first", "transactions" + i));
            }

            // 模拟异常。看看消息是否发送,结果没发送
            int i = 1 / 0;

            // 提交事务
            kafkaProducer.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            // 终止事务
            kafkaProducer.abortTransaction();
        } finally {
            // 3.关闭资源
            kafkaProducer.close();
        }
    }
}
