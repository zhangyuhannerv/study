package com.study.springboot2study.orm.mybatis.controller;

import com.study.springboot2study.bean.TestUser;
import com.study.springboot2study.orm.mybatis.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TestUserController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/5
 * @Version 1.0
 */

@Controller
public class TestUserController {
    @Autowired
    TestUserService testUserService;

    @RequestMapping("/getTestUserById")
    @ResponseBody
    public TestUser getById(int id) {
        return testUserService.getById(id);
    }
}
