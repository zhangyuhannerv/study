package com.study.springboot2study.controller;

import com.study.springboot2study.bean.Car;
import com.study.springboot2study.testYml.TestYmlPerson;
import com.study.springbootstarterhelloautoconfigure.hello.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

// @TestUserController
// @ResponseBody
@RestController// 上面两个注解的合体

// 使用lombok自动注入日志类
@Slf4j
public class HelloController {
    @Autowired
    Car car;
    @Autowired
    TestYmlPerson person;

    @Autowired
    HelloService helloService;

    // @RequestMapping("/")
    // public String index() {
    //     return "欢迎页";
    // }

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

    /**
     * 测试访问静态资源名，会发生什么，结果就是，不再访问静态资源，而是走这个接口，网页显示aaa
     * 结论就是请求先看controller，再看静态资源。
     *
     * @return
     */
    @RequestMapping("/1.png")
    public String testStaticRequest() {
        return "aaa";
    }


    @GetMapping("/sayHello")
    public String sayHello() {
        return helloService.sayHello("自定义的starter");
    }
}
