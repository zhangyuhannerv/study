package com.study.springboot2study.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局的异常处理器（处理整个web的异常）,这也是开发中推荐的方式
 * @Author Zhangyuhan
 * @Date 2022/4/25
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 专门处理数字运算的异常
     *
     * @return
     */
    @ExceptionHandler({ArithmeticException.class, NullPointerException.class})// 代表当前是一个异常处理器
    public String handlerArithException(Exception e) {

        log.error("异常是：{}", e);

        return "login";// 返回视图地址
    }
}
