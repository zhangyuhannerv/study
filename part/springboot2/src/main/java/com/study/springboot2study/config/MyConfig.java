package com.study.springboot2study.config;

import com.study.springboot2study.bean.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @ClassName MyConfig
 * @Description 1.配置类，里面可以使用@Bean标注在方法上，给容器注册组件，默认是单实例。
 * 2.值得注意的是，配置类本身也是个组件。即被@Configuration标注的类也在ioc容器里面被注册成了个bean实例
 * 3.@Configuration的proxyBeanMethods默认值为true。
 * springboot两种底层代理（FULL:全代理，Configuration的proxyBeanMethods=true;LITE:轻量级代理,Configuration的proxyBeanMethods默认值为false）
 * 这两种代理方式主要是为了解决组件依赖
 * 4.@Import，可以放在任何一个组件类上，给容器中自动创建某些类型的组件,该注解导入的组件的名字（id)默认是全类名
 * 5.@Conditional是一个条件装配注解。可以加在类上，也可以加在方法上。它有很多派生注解。目的是为了满足特定的条件才会注入组件。
 * 6.@ImportResource("classpath:beans.xml")，可以导入spring的配置文件
 * @Author Zhangyuhan
 * @Date 2022/3/22
 * @Version 1.0
 */

@Import({User.class})// 该注解导入的组件的名字（id)默认是全类名
@Configuration(proxyBeanMethods = true)// 告诉springboot这是一个配置类，以前在xml里能做什么，现在在这个类里就能做什么
@ImportResource("classpath:beans.xml")// 引入xml配置文件。该注解一般加载配置类上
// 1.开启Car组件的属性配置绑定，2把指定的组件注入到容器中
@EnableConfigurationProperties(Car.class)
public class MyConfig {

    /**
     * 外部无论对配置类中的这个组件注册方法调用多少次，获取的都是之前注册到ioc容器中的单实例对象
     * 原因就是@Configuration的proxyBeanMethods的属性值为true
     *
     * @return
     */
    @Bean// 该注解就相当于给容器中添加组件(bean实例)。以方法名作为组件的id,以返回类型作为组件类型。返回的值，就是组件在容器中的实例
    public User user01() {
        User user = new User("张三", "18");
        user.setPet(tomcatPet());
        return user;
    }

    @Bean("tom")// 可以给bean实例一个自定义的名字
    public Pet tomcatPet() {
        return new Pet("tomcat");
    }

    // @Bean
    public Book book() {
        return new Book();
    }

    @ConditionalOnBean(name = "book")// 只有容器中有名为book的组件时，才创建person组件，该注解也可以加到类上。条件成里。类里所有的组件才能成功注入
    @Bean
    public Person person() {
        return new Person();
    }
}
