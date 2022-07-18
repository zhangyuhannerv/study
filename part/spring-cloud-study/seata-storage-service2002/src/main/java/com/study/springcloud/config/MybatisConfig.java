package com.study.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.study.springcloud.dao"})
public class MybatisConfig {
}
