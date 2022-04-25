package com.study.springboot2study.thymeleafController;

import com.study.springboot2study.bean.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    @GetMapping("/basic_table")
    public String basicTable() {
        // 测试springboot的自定义异常
        int i = 10 / 0;
        return "table/basic_table";
    }

    @GetMapping("/dynamic_table")
    public String dynamicTable(Model model) {
        // 这里动态填写表格的内容
        List<LoginUser> loginUsers = Arrays.asList(new LoginUser("张三", "1234"),
                new LoginUser("李四", "aaa1"),
                new LoginUser("王五", "bbbb"));

        model.addAttribute("users", loginUsers);

        return "table/dynamic_table";
    }

    @GetMapping("/responsive_table")
    public String responsiveTable() {
        return "table/responsive_table";
    }

    @GetMapping("/editable_table")
    public String editableTable() {
        return "table/editable_table";
    }
}
