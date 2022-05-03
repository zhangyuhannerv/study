package com.study.springboot2study.servlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @ClassName MyRegisConfig
 * @Description 通过注册bean的方式来注入原生的servlet, listener, filter
 * @Author Zhangyuhan
 * @Date 2022/5/4
 * @Version 1.0
 */
// 注意有两个细节
// 1.(proxyBeanMethods = true):保证依赖的组件始终是单实例的
// 2.为什么原生的servlet没有被spring的interceptor拦截？
@Configuration
public class MyRegisConfig {
    @Bean
    public ServletRegistrationBean<MyServlet> myServlet() {
        MyServlet myServlet = new MyServlet();
        return new ServletRegistrationBean<>(myServlet, "/myServlet", "/myServlet1");
    }

    @Bean
    public FilterRegistrationBean<MyFilter> myFilter() {
        MyFilter myFilter = new MyFilter();
        // 拦截特定的servlet
        // return new FilterRegistrationBean<>(myFilter, myServlet());
        // 拦截某些特定的路径
        FilterRegistrationBean<MyFilter> myFilterFilterRegistrationBean = new FilterRegistrationBean<>(myFilter);
        myFilterFilterRegistrationBean.setUrlPatterns(Arrays.asList("/css/*", "/images/*"));
        return myFilterFilterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<MyServletContextListener> myListener() {
        MyServletContextListener myServletContextListener = new MyServletContextListener();
        return new ServletListenerRegistrationBean<>(myServletContextListener);
    }
}
