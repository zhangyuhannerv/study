package com.study.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
// 针对CLOUD-PAYMENT-SERVICE服务，使用手动指定的负载均衡策略
//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfLbRule.class)
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
