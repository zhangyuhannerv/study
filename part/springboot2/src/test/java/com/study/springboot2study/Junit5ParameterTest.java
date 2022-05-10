package com.study.springboot2study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

/**
 * @ClassName Junit5ParameterTest
 * @Description Junit5参数化测试
 * @Author Zhangyuhan
 * @Date 2022/5/7
 * @Version 1.0
 */
public class Junit5ParameterTest {
    /**
     * 一个方法，提供字符串流
     *
     * @return
     */
    static Stream<String> stringProvider() {
        return Stream.of("apple", "banana");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("参数化测试")
    void testParameterized(int i) {
        System.out.println(i + 1);
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    @DisplayName("参数化测试(参数来源是方法)")
    void testParameterized(String s) {
        System.out.println(s);
    }
}
