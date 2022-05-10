package com.study.springboot2study.config;

import com.study.springboot2study.interceptor.LoginInterceptor;
import com.study.springboot2study.interceptor.UrlAccessCountStatByRedisInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @ClassName AdminWebConfig
 * @Description 配置拦截器
 * 实现步骤：
 * 1.编写一个拦截器，实现HandlerInterceptor接口
 * 2.拦截器注册到容器中（实现WebMvcConfigurer)
 * 3.指定拦截规则（如果是拦截所有=>/**,静态资源也会被拦截）
 * @Author Zhangyuhan
 * @Date 2022/4/21
 * @Version 1.0
 */

// @EnableWebMvc // 全面接管springmvc,静态资源，视图解析器，欢迎页...所有spring官方的mvc自动配置全部失效,该注解要慎用
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Autowired
    UrlAccessCountStatByRedisInterceptor urlAccessCountStatByRedisInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")// 添加拦截哪些请求,/**是拦截所有请求，注意此时静态资源也被拦截了
                .excludePathPatterns("/", "/login", "/loginValid", "/css/**", "/fonts/**", "/images/**", "/js/**"); // 放行哪些请求

        // 注意如果是new出来的，那么UrlAccessCountStatByRedisInterceptor里面注入RedisTemplate就没法用
        // 因为只有容器中的组件，spring才会解析@Autowired等spring家的注解
        registry.addInterceptor(urlAccessCountStatByRedisInterceptor)
                .addPathPatterns("/**")// 添加拦截哪些请求,/**是拦截所有请求，注意此时静态资源也被拦截了
                .excludePathPatterns("/css/**", "/fonts/**", "/images/**", "/js/**"); // 放行哪些请求

    }

    /**
     * 定义静态资源行为
     *
     * @param registry
     */
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        *//**
     * 访问 /aa/**下的所有请求都去 classpath:/static/ 下面进行匹配
     *//*
        registry.addResourceHandler("/aa/**")
                .addResourceLocations("classpath:/static/");
    }*/

    /**
     * 非常底层的定制化
     * @return
     */
    /*@Bean
    public WebMvcRegistrations webMvcRegistrations() {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return WebMvcRegistrations.super.getRequestMappingHandlerMapping();
            }

            @Override
            public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
                return WebMvcRegistrations.super.getRequestMappingHandlerAdapter();
            }

            @Override
            public ExceptionHandlerExceptionResolver getExceptionHandlerExceptionResolver() {
                return WebMvcRegistrations.super.getExceptionHandlerExceptionResolver();
            }
        };
    }*/
}
