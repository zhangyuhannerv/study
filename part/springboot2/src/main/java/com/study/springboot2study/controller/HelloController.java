package com.study.springboot2study.controller;

import com.study.springboot2study.bean.Car;
import com.study.springboot2study.testYml.TestYmlPerson;
import lombok.extern.slf4j.Slf4j;
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

// 使用lombok自动注入日志类
@Slf4j
public class HelloController {
    @Autowired
    Car car;
    @Autowired
    TestYmlPerson person;

    @RequestMapping("/hello")
    public String handle01() {
        log.info("测试jrebel");
        return "Hello,springboot2,你好";
    }

    @RequestMapping("/car")
    public Car car() {
        log.info("访问了car接口");
        log.info("测试devtools");
        return car;
    }

    @RequestMapping("/testYml")
    public TestYmlPerson testYml() {
        log.info(person.getUserName());
        return person;
    }
}
