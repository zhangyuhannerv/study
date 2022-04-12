package com.study.springboot2study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    // 测试同一个请求域转发获取数据
    @GetMapping("/goto")
    public String goToPage(HttpServletRequest request) {
        request.setAttribute("msg", "成功了");
        request.setAttribute("code", 200);
        return "forward:success";
    }

    // Map,Model,Request放值是等价的。都是向同一个请求域中房入数据，都可以用request.getAttribute()进行获取，或者页面用el表达式取值。
    @GetMapping("/params")
    public String testParams(Map<String, Object> map,
                             Model model,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        map.put("hello", "world666");
        model.addAttribute("world", "hello666");
        request.setAttribute("message", "hello world");

        Cookie cookie = new Cookie("c1", "v1");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
        return "forward:/success";
    }

    @GetMapping("/success")
    @ResponseBody
    public Map<String, Object> success(@RequestAttribute(value = "msg", required = false) String msg,
                                       @RequestAttribute(value = "code", required = false) Integer code,
                                       HttpServletRequest request) {
        // 第一个转发
        Object msg1 = request.getAttribute("msg");
        Object code1 = request.getAttribute("code");

        // 第二个转发
        Object hello = request.getAttribute("hello");
        Object world = request.getAttribute("world");
        Object message = request.getAttribute("message");

        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("code", code);
        map.put("msg1", msg1);
        map.put("code1", code1);


        map.put("hello", hello);
        map.put("world", world);
        map.put("message", message);
        return map;
    }

    // ---

}
