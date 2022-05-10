package com.study.springboot2study.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestAutoByProfile
 * @Description 测试通过激活配置文件的不同来进行自动装配功能
 * @Author Zhangyuhan
 * @Date 2022/5/9
 * @Version 1.0
 */

@Data
public abstract class TestAutoByProfile {
    private String name;
    private Integer age;
}
