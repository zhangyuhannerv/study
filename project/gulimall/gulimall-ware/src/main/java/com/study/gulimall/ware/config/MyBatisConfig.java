package com.study.gulimall.ware.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.study.gulimall.ware.dao")
public class MyBatisConfig {
    /**
     * 添加分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor interceptor = new PaginationInterceptor();
        // 超过最大分页数，查询第一页的数据
        interceptor.setOverflow(true);
        // 最大每页的数量
        interceptor.setLimit(1000);
        return interceptor;
    }
}
