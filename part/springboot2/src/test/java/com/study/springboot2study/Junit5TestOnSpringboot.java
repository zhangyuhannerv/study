package com.study.springboot2study;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @ClassName Junit5TestOnSpringboot
 * @Description junit5在springboot下的基础使用（配合test-starter使用）
 * 具有spring的功能如：@Autowired,@Transactional等
 * @Author Zhangyuhan
 * @Date 2022/5/6
 * @Version 1.0
 */
@Slf4j
@SpringBootTest
public class Junit5TestOnSpringboot {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    void test1() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String s = operations.get("/main");
        log.info("现在/main的数量是{}", s);
    }
}
