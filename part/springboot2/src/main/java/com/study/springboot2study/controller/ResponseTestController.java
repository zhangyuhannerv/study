package com.study.springboot2study.controller;

import com.study.springboot2study.pojo.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @ClassName ResponseTestController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/14
 * @Version 1.0
 */

@Controller
public class ResponseTestController {

    // 注意，当在pom里加了xml处理工具包之后，因为请求头里xml的优先级高于JSON，所以，此时访问此接口，就会返回xml格式的数据
    // 另外，如果开启了基于参数的内容协商(在yml里)，那么访问/person?format=json，就返回json；访问/person?format=xml,就返回xml
    @GetMapping("/person")
    // @ResponseBody能给前端返回数据包，数据格式如何视请求头格式而定，大部分情况下都是json
    @ResponseBody
    public Person getPerson() {
        Person person = new Person();
        person.setAge(28);
        person.setBirth(new Date());
        person.setUserName("zhansan");
        return person;
    }
}
