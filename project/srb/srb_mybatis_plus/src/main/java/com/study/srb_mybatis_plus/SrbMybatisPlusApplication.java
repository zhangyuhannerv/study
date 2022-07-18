package com.study.srb_mybatis_plus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.study.srb_mybatis_plus.mapper")
public class SrbMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrbMybatisPlusApplication.class, args);
    }

}
