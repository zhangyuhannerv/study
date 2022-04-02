package com.study.springboot2study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RequestController
 * @Description 测试@RequestAttribute注解(一般是转发到页面时，在request域里放值，然后前台用el表达式取值。这里没配置模板引擎。所以模拟一个转发操作取值)
 * @Author Zhangyuhan
 * @Date 2022/4/2
 * @Version 1.0
 */
@Controller
public class RequestController {
    @GetMapping("/goto")
    public String goToPage(HttpServletRequest request) {
        request.setAttribute("msg", "成功了");
        request.setAttribute("code", 200);
        return "forward:success";
    }

    @GetMapping("/success")
    @ResponseBody
    public Map<String, Object> success(@RequestAttribute("msg") String msg,
                                       @RequestAttribute("code") Integer code,
                                       HttpServletRequest request) {
        Object msg1 = request.getAttribute("msg");
        Object code1 = request.getAttribute("code");

        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("code", code);
        map.put("msg1", msg1);
        map.put("code1", code1);
        return map;
    }
}
