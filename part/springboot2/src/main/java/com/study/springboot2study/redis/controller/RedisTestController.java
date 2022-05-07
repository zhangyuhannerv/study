package com.study.springboot2study.redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName RedisTestController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/6
 * @Version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/redis")
public class RedisTestController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;// k,v都是String
    @Autowired
    RedisConnectionFactory redisConnectionFactory;


    @RequestMapping("/test")
    @ResponseBody
    public void test() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("hello", "world");
        String hello = operations.get("hello");
        log.info("值是{}", hello);
        log.info("redis连接工厂的类型是{}", redisConnectionFactory.getClass());
    }
}
