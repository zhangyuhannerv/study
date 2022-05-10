package com.study.springboot2study.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestProfilePrecedence
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/9
 * @Version 1.0
 */

@Component
@ConfigurationProperties("test-precedence")
@Data
public class TestProfilePrecedence {
    private String value;
}
