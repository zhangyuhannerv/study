package com.study.springboot2study;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName Junit5AssertTest
 * @Description Junit5的断言测试使用
 * @Author Zhangyuhan
 * @Date 2022/5/7
 * @Version 1.0
 */

public class Junit5AssertTest {

    int cal(int i, int j) {
        return i + j;
    }


    /**
     * 断言：前面的断言失败，后面的断言则不会执行（实际上是后面的代码都不执行）
     * 断言的方法，前面传的都是期望的，后面传的都是真正的
     */
    @DisplayName("测试简单断言")
    @Test
    void testSimpleAssertion() {
        int cal = cal(2, 3);
        // 断言判定一定要相等
        assertEquals(5, cal);
        // assertEquals(6, cal);
        // assertEquals(6, cal, "业务逻辑计算失败");// 判断是否相等，通过equals()判断

        Object obj1 = new Object();
        Object obj2 = new Object();
        assertSame(obj2, obj1, "两个对象引用地址不相同");// 判断是否相等，通过引用地址判断
    }

    @DisplayName("测试数组断言")
    @Test
    void testArrayAssertion() {
        int[] arr1 = {1, 2};
        int[] arr2 = {1, 2};
        int[] arr3 = {2, 1};
        // assertArrayEquals(arr1, arr2);
        assertArrayEquals(arr1, arr3, "数组内容不相等");
    }

    /**
     * 所有断言都成功，整体才成功，有一个不成功，那就失败
     */
    @DisplayName("测试组合断言")
    @Test
    void all() {
        assertAll("这是组合断言的标题",
                // () -> assertTrue(true && true),
                () -> assertTrue(true && false, "结果不是true"),
                () -> assertEquals(1, 1, "结果不是1"));
    }

    @DisplayName("测试异常断言")
    @Test
    void testException() {
        // 断言一定会抛出某个异常
        assertThrows(ArithmeticException.class, () -> {
            // int i = 10 / 0;// 此时断言是成功的，控制台无任何输出
            int i = 10 / 1;// 此时断言是失败的，才会打印出信息
        }, "没有抛出数学运算异常，居然正常运行，和我们期待的不符");
    }

    @DisplayName("测试超时断言")
    @Test
    void timeout() {
        // 断言执行时间一定在1s内，超出1s则断言失败
        assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(1500), "执行时间居然超过了1s，和我们期待的不符");
    }

    @DisplayName("快速失败")
    @Test
    void shouldFail() {
        // int i = 1;
        int i = 0;
        if (i != 1) {
            fail("测试失败");
        }
    }
}

