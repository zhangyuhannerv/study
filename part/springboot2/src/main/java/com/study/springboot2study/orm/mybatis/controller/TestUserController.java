package com.study.springboot2study.orm.mybatis.controller;

import com.study.springboot2study.bean.TestUser;
import com.study.springboot2study.orm.mybatis.service.ITestUserService;
import com.study.springboot2study.orm.mybatis.service.impl.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    ITestUserService testUserService;

    @RequestMapping("/getTestUserById")
    @ResponseBody
    public TestUser getById(int id) {
        return testUserService.getById(id);
    }

    @RequestMapping("/getTestUserById2")
    @ResponseBody
    public TestUser getById2(int id) {
        return testUserService.getById2(id);
    }

    @RequestMapping("/insertTestUser")
    @ResponseBody
    public TestUser insertTestUser(TestUser testUser) {
        return testUserService.insertTestUser(testUser);
    }

    @RequestMapping("/insertTestUser2")
    @ResponseBody
    public TestUser insertTestUser2(TestUser testUser) {
        return testUserService.insertTestUser2(testUser);
    }

    @RequestMapping("/listTestUser")
    @ResponseBody
    public List<TestUser> listTestUser() {
        return testUserService.list();
    }

    @RequestMapping("/deleteTestUserById/{id}")
    public String deleteUser(@PathVariable("id") Long id,
                             @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                             RedirectAttributes re) {
        testUserService.removeById(id);
        re.addAttribute("pn", pn);
        return "redirect:/dynamic_table";
    }
}
