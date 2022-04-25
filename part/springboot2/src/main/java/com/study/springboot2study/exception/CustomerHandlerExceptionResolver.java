package com.study.springboot2study.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName CustomerHandlerExceptionResolver
 * @Description 自定义的异常解析器
 * @Author Zhangyuhan
 * @Date 2022/4/26
 * @Version 1.0
 */

// 为了让自己写的HandlerExceptionResolver生效，让他拥有最高优先级（数字越小，优先级越高），此时它就是全局的默认异常处理器
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class CustomerHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        try {
            response.sendError(511, "我喜欢的错误");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView();

        // return new ModelAndView("login");
    }
}
