package com.study.springboot2study.testYml;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestYmlPet
 * @Description 测试yml的pet对象
 * @Author Zhangyuhan
 * @Date 2022/3/25
 * @Version 1.0
 */

@Component
@ConfigurationProperties(prefix = "pet")
@ToString
@Data
public class TestYmlPet {
    private String name;
    private Double weight;
}
