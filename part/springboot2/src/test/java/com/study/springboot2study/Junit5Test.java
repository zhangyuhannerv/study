package com.study.springboot2study;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Junit5Test
 * @Description 测试Junit5的功能，建议所有测试类的类名一定要以xxxTest来命名
 * @Author Zhangyuhan
 * @Date 2022/5/6
 * @Version 1.0
 */

/**
 * @SpringBootTest是一个复合注解，证明这是以springboot驱动测试
 * @BootstrapWith(SpringBootTestContextBootstrapper.class)
 * @ExtendWith({SpringExtension.class})
 */
@SpringBootTest
@DisplayName("Junit5功能测试类")
public class Junit5Test {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @DisplayName("测试DisplayName注解")
    @Test
    void testDisplayName() {
        System.out.println(1);
    }

    @DisplayName("测试方法2")
    @Test
    void test2() {
        System.out.println(2);
    }

    @Disabled
    @DisplayName("测试@Disabled")
    @Test
    void test3() {
        System.out.println(3);
    }

    /**
     * @throws InterruptedException
     * @Timeout能规定方法的超时时间，超出时间则测试结果异常
     */
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)// 超过500ms就报错
    @DisplayName("测试@Timeout")
    @Test
    void test4() throws InterruptedException {
        Thread.sleep(600);
        System.out.println(4);
    }

    @DisplayName("测试spring的自动注入")
    @Test
    void test5() {
        System.out.println(jdbcTemplate);
        // 打印null值
        // 如果需要使用spring的功能，那么需要在类上加上@SpringBootTest
    }

    @DisplayName("测试重复测试注解：@RepeatedTest")
    // 重复测试5次
    @RepeatedTest(5)
    void test6() {
        System.out.println(6);
    }

    @BeforeEach
    void testBeforeEach() {
        System.out.println("测试开始了");
    }

    @AfterEach
    void testAfterEach() {
        System.out.println("测试结束了");
    }

    @BeforeAll
    static void testBeforeAll() {
        System.out.println("所有测试就要开始了");
    }

    @AfterAll
    static void testAfterAll() {
        System.out.println("所有测试已经结束了");
    }
}
