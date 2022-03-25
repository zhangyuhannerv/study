package com.study.springboot2study.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName Car
 * @Description
 * @Author Zhangyuhan
 * @Date 2022/3/24
 * @Version 1.0
 */

// 注意：只有在容器中的组件，才会拥有springboot提供的强大功能
// 也可以不使用Component注解，去配置类开启某些类的属性配置绑定，这样也会实现组件注册。这适用于给第三方包开启属性配置绑定功能。
// @Component
// 属性配置绑定，注意只能绑定spring的核心配置文件，即application.properties。别的自定义命名的配置文件绑定不上
@ConfigurationProperties(prefix = "mycar")
@Data //getter()和setter()
@ToString // 重写toString()
@AllArgsConstructor// 全参构造器
@NoArgsConstructor// 无参构造器
public class Car {
    private String brand;
    private Integer price;
}
