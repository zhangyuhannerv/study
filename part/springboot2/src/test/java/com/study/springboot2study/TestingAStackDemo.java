package com.study.springboot2study;

import java.util.EmptyStackException;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName TestingAStackDemo
 * @Description 嵌套测试
 * @Author Zhangyuhan
 * @Date 2022/5/7
 * @Version 1.0
 */
@DisplayName("嵌套测试")
class TestingAStackDemo {

    Stack<Object> stack;

    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew() {
        new Stack<>();
        // assertNotNull(stack);// 断言失败，因为下面的内层的@BeforeEach不执行
        // 结论：
        // 嵌套测试情况下，外层的test不能驱动内层的BeforeEach/BeforeAll/AfterEach/AfterAll...之类的方法提前/之后运行
        // 但是，内层的test可以驱动外层的BeforeEach/BeforeAll/AfterEach/AfterAll...之类的方法提前/之后运行
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createNewStack() {
            stack = new Stack<>();
        }

        @Test
        @DisplayName("is empty")
        void isEmpty() {
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            assertThrows(EmptyStackException.class, stack::peek);
        }

        @Nested
        @DisplayName("after pushing an element")
        class AfterPushing {

            String anElement = "an element";

            @BeforeEach
            void pushAnElement() {
                stack.push(anElement);
            }


            @Test
            @DisplayName("it is no longer empty")
            void isNotEmpty() {
                /**
                 * 注意此时先是驱动了外层的@BeforeEach，新new了一个栈
                 * 然后驱动了本层的@BeforeEach,向里面放了一个元素
                 */
                assertFalse(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped() {
                assertEquals(anElement, stack.pop());
                assertTrue(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked() {
                assertEquals(anElement, stack.peek());
                assertFalse(stack.isEmpty());
            }
        }
    }
}
