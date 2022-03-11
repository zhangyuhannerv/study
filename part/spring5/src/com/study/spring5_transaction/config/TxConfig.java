package com.study.spring5_transaction.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @ClassName TxConfig
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/11
 * @Version 1.0
 */
@Configuration// 配置类
@ComponentScan(basePackages = "com.study.spring5_transaction")// 包扫描路径
@EnableTransactionManagement// 开启事务
public class TxConfig {
    // 创建数据库链接池
    // 下面这段代码和在xml里的含义是一样的，在IOC容器创建一个链接池对象
    @Bean
    public DruidDataSource getDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:10000/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    // 创建JdbcTemplate对象
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        // 此时如果参数写了DataSource，那么就会去IOC容器中根据类型找到DataSource对象，自动注入
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        // 注入DataSource对象
        // 第一种写法,调用方法。缺点：dataSource创建了两次，第一次是在ioc容器中，第二次是在这里。重复创建。
        // jdbcTemplate.setDataSource(getDruidDataSource());
        // 第二种写法。注入
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    // 创建事务管理器
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
