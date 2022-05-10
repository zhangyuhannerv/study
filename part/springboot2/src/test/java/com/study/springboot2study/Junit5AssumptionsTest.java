package com.study.springboot2study;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @ClassName Junit5AssumptionsTest
 * @Description assumptions:前置条件
 * 注意：前置条件和断言不同
 * 断言失败，则测试失败
 * 前置条件失败，则测试终止（可以理解为某个测试实例的前置条件）
 * @Author Zhangyuhan
 * @Date 2022/5/7
 * @Version 1.0
 */
public class Junit5AssumptionsTest {

    /**
     * 测试前置条件
     */
    @DisplayName("测试前置条件")
    @Test
    void testAssumptions() {
        // boolean res = true;
        boolean res = false;
        // assumeTrue()要求前置条件是true才能继续向下执行。是false，则抛出信息，并且该测试结果不是失败而是跳过，此时和@Disabled效果一样
        Assumptions.assumeTrue(res, "结果不是true");
        System.out.println("111");
    }
}
