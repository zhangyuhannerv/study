package com.zh.testTransactionInvalid.controller;

import com.zh.testTransactionInvalid.service.ITestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TestUserController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/19
 * @Version 1.0
 */
@Controller
public class TestUserController {
    @Autowired
    ITestUserService testUserService;

    @RequestMapping("/delete")
    @ResponseBody
    public boolean delete() {
        return testUserService.testFunc();
    }
}
