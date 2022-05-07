package com.study.springboot2study.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @ClassName MyFilter
 * @Description 原生的filter
 * filter和Interceptor几乎拥有相同的功能，都是起到拦截的作用，那么用哪个好?或者说两者有什么区别
 * 1.filter是servlet的原生组件，好处是脱离spring也能使用
 * 2.interceptor是spring定义的接口，只能在spring技术栈里使用。好处是可以使用spring的自动装配功能和其它spring拥有的特性。
 * @Author Zhangyuhan
 * @Date 2022/5/4
 * @Version 1.0
 */
@Slf4j
// @WebFilter(urlPatterns = {"/css/*", "/images/*"})
// 补充说明：/*是servlet里的原生写法，/**是spring家的写法
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter初始化完成");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("MyFilter工作");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("MyFilter销毁");
        Filter.super.destroy();
    }
}
