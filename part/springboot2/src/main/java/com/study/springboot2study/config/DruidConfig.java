package com.study.springboot2study.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

/**
 * @ClassName DruidConfig
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/4
 * @Version 1.0
 */
// 这是使用配置的方式，亦可以直接使用druid官方的starter
// @Configuration
@Deprecated
public class DruidConfig {

    /**
     * 定制化为druid的连接池
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource")// 属性绑定
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        // 开启druid的sql监控功能，和监控页面配合使用
        // 开启druid的sql防火墙功能
        // 因为使用了@ConfigurationProperties，所以除了在这里写，也可以写在配置文件里
        // dataSource.setFilters("stat,wall");
        return dataSource;
    }

    /**
     * 配置druid的监控页面
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        StatViewServlet statViewServlet = new StatViewServlet();
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(statViewServlet, "/druid/*");
        // 配置监控页面访问的用户和密码
        servletRegistrationBean.addInitParameter("loginUsername", "druid");
        servletRegistrationBean.addInitParameter("loginPassword", "druid");
        return servletRegistrationBean;
    }

    /**
     * WebStatFilter用于采集web-jdbc关联监控的数据
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        WebStatFilter webStatFilter = new WebStatFilter();
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(webStatFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
