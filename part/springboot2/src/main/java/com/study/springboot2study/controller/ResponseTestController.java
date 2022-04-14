package com.study.springboot2study.controller;

import com.study.springboot2study.pojo.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName ResponseTestController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/14
 * @Version 1.0
 */

@Controller
public class ResponseTestController {
    @GetMapping("/person")
    public Person getPerson() {
        return new Person();
    }
}
