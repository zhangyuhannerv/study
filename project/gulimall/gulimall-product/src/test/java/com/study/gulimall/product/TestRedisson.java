package com.study.gulimall.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TestRedisson {
    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testRedissonClient() {
        System.out.println(redissonClient);
    }
}
