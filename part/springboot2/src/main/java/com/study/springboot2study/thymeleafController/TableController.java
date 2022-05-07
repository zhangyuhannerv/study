package com.study.springboot2study.thymeleafController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.springboot2study.bean.LoginUser;
import com.study.springboot2study.bean.TestUser;
import com.study.springboot2study.exception.UserTooManyException;
import com.study.springboot2study.orm.mybatis.service.ITestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName TableController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/19
 * @Version 1.0
 */
@Controller
public class TableController {
    @Autowired
    ITestUserService testUserService;

    @GetMapping("/basic_table")
    public String basicTable() {
        // 测试springboot的异常
        int i = 10 / 0;
        return "table/basic_table";
    }

    @GetMapping("/dynamic_table")
    public String dynamicTable(
            // pn是当前页
            @RequestParam(value = "pn", defaultValue = "1") Integer pn,
            Model model) {
        // 这里动态填写表格的内容
        // List<LoginUser> loginUsers = Arrays.asList(new LoginUser("张三", "1234"),
        //         new LoginUser("李四", "aaa1"),
        //         new LoginUser("王五", "bbbb"));
        //
        // model.addAttribute("users", loginUsers);

        // 动态表格的内容替换为从数据库里查
        // 查询所有
        // List<TestUser> list = testUserService.list();
        // model.addAttribute("users", list);

        // 分页查询
        Page<TestUser> testUserPage = new Page<>(pn, 2);
        Page<TestUser> page = testUserService.page(testUserPage);
        model.addAttribute("page", page);


        // 测试springboot的异常
        // if (loginUsers.size() > 1) {
        //     throw new UserTooManyException();
        // }

        return "table/dynamic_table";
    }

    @GetMapping("/responsive_table")
    // 测试springboot的异常
    public String responsiveTable(int a) {
        // public String responsiveTable() {
        return "table/responsive_table";
    }

    @GetMapping("/editable_table")
    public String editableTable() {
        return "table/editable_table";
    }
}
