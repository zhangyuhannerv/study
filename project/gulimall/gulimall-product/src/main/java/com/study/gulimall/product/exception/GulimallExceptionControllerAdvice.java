package com.study.gulimall.product.exception;

import com.study.common.exception.BizCodeEnum;
import com.study.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 集中处理所有异常
 */
@RestControllerAdvice(basePackages = "com.study.gulimall.product.controller")
@Slf4j
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("出现异常，异常信息：{}，异常类型：{}", e.getMessage(), e.getClass());
        BindingResult result = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        // 获取校验的错误结果
        result.getFieldErrors().forEach(item -> {
            // 字段名称,字段错误提示
            map.put(item.getField(), item.getDefaultMessage());
        });
        // 校验失败
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(),
                BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", map);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable e) {
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(),
                BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}
