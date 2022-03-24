package com.study.springboot2study.controller;

import com.study.springboot2study.bean.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/21
 * @Version 1.0
 */

// @Controller
// @ResponseBody
@RestController// 上面两个注解的合体
public class HelloController {
    @Autowired
    Car car;

    @RequestMapping("/hello")
    public String handle01() {
        return "Hello,springboot2,你好";
    }

    @RequestMapping("/car")
    public Car car() {
        return car;
    }
}
