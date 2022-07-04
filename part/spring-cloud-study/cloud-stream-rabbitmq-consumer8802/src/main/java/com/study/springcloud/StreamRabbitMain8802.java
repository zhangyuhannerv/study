package com.study.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StreamRabbitMain8802 {
    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitMain8802.class, args);
    }
}
