package com.study.springboot2study.config;

import com.study.springboot2study.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName AdminWebConfig
 * @Description 配置拦截器
 * 实现步骤：
 * 1.编写一个拦截器，实现HadlerInterceptor接口
 * 2.拦截器注册到容器中（实现WebMvcConfigurer)
 * 3.指定拦截规则（如果是拦截所有=>/**,静态资源也会被拦截）
 * @Author Zhangyuhan
 * @Date 2022/4/21
 * @Version 1.0
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")// 添加拦截哪些请求,/**是拦截所有请求，注意此时静态资源也被拦截了
                .excludePathPatterns("/", "/login", "/loginValid", "/css/**", "/fonts/**", "/images/**", "/js/**"); // 放行哪些请求
    }
}
