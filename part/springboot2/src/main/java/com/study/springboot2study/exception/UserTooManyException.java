package com.study.springboot2study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ClassName UserTooManyException
 * @Description 自定义用户太多异常
 * @Author Zhangyuhan
 * @Date 2022/4/25
 * @Version 1.0
 */

// 给自定义异常加个状态码和原因
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "用户数量太多")
public class UserTooManyException extends RuntimeException {

    public UserTooManyException() {

    }

    public UserTooManyException(String message) {
        super(message);
    }

}
