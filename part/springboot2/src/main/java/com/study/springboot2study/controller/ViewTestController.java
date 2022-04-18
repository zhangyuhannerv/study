package com.study.springboot2study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ViewTestController
 * @Description 测试视图解析功能。此时的模板是thymeleaf
 * @Author Zhangyuhan
 * @Date 2022/4/18
 * @Version 1.0
 */
@Controller
@RequestMapping("/thymeleafTest")
public class ViewTestController {
    @GetMapping("/helloThymeLeaf")
    public String success(Model model) {
        // model中的数据会被放在请求域中，相当于执行了request.setAttribute()
        model.addAttribute("msg", "你好，thymeleaf");
        model.addAttribute("link", "https://www.bilibili.com/");
        return "success";
    }
}
