package com.study.shirospringboot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ShiroConfig
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/14
 * @Version 1.0
 */

@Configuration
public class ShiroConfig {
    // ShiroFilterFactoryBean
    //  第三步
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        // 添加shiro的内置过滤器
        /**
         *  anno：无需认证就可以访问
         *  authc：必须认证才能访问
         *  user：必须拥有记住我功能才能让访问
         *  perms：拥有对某个资源的权限才能访问
         *  roles：拥有某个角色权限才能访问
         */


        Map<String, String> filterMap = new LinkedHashMap<>();


        // 设置需要授权才能访问的资源,正常情况下，没有授权会跳转到未授权页面
        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");


        // filterMap.put("/user/add", "authc");
        // filterMap.put("/user/update", "authc");
        // 上述两行代码支持通配符
        // 拦截
        filterMap.put("/user/*", "authc");

        bean.setFilterChainDefinitionMap(filterMap);

        // 设置登陆的请求
        bean.setLoginUrl("/toLogin");
        // 设置未授权页面
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }

    // DefaultWebSecurityFactory
    // 第二步、
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getUserRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    // 创建realm对象，需要自定义类
    // 第一步
    @Bean
    public UserRealm getUserRealm() {
        return new UserRealm();
    }

    // 配置shiroDialect：用来shiro与thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
