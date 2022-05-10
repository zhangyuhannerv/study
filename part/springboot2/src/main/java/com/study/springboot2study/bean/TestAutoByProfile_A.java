package com.study.springboot2study.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestAutoByProfile_A
 * @Description 只有在dev配置文件激活的时候才有该组件
 * 注意：@Profile可以标注在类上，还可以标注在方法上。
 * 例：@Configuration里的@Bean后面也可以加个该注解,那么表示在某种特定的配置下，才加载某个组件
 * @Author Zhangyuhan
 * @Date 2022/5/9
 * @Version 1.0
 */
// 这种写法是在没有环境标识的配置文件（默认）/或者有dev的配置文件条件才加载
// @Profile(value = {"dev","default"})
@Profile("dev")
@Component
@ConfigurationProperties("test-bean")
public class TestAutoByProfile_A extends TestAutoByProfile {
}
