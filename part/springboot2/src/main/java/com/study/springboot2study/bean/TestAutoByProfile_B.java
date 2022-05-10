package com.study.springboot2study.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestAutoByProfile_B
 * @Description 只有在prod配置文件激活的时候才有该组件
 * @Author Zhangyuhan
 * @Date 2022/5/9
 * @Version 1.0
 */

@Profile("test")
@Component
@ConfigurationProperties("test-bean")
public class TestAutoByProfile_B extends TestAutoByProfile {
}
