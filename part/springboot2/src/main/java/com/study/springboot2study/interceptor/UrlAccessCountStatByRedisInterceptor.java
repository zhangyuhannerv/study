package com.study.springboot2study.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UrlAccessCountStatByRedisInterceptor
 * @Description 统计url的访问情况，统计结果存储在redis里
 * @Author Zhangyuhan
 * @Date 2022/5/6
 * @Version 1.0
 */
// 将该组件注入到ioc容器中。让注入的RedisTemplate生效
@Component
public class UrlAccessCountStatByRedisInterceptor implements HandlerInterceptor {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String requestURI = request.getRequestURI();
        // 每次访问当前uri就会计数加1
        operations.increment(requestURI.replaceFirst(request.getServletContext().getContextPath(), ""));
        return true;
    }
}
