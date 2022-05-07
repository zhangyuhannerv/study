package com.study.springboot2study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName Junit5Test
 * @Description 测试Junit5的功能
 * @Author Zhangyuhan
 * @Date 2022/5/6
 * @Version 1.0
 */

@DisplayName("Junit5功能测试类")
public class Junit5Test {
    @DisplayName("测试DisplayName注解")
    @Test
    void testDisplayName() {
        System.out.println(1);
    }
}
