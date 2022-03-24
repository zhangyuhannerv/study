package com.study.springboot2study.bean;

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
public class Car {
    private String brand;
    private Integer price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
