package com.study.springboot2adminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer// 开启管理服务功能
public class Springboot2AdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2AdminServerApplication.class, args);
    }

}
