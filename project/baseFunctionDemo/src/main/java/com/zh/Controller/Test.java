package com.zh.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName Test
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/7/27
 * @Version 1.0
 */
@Controller
public class Test {
    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "spring-boot项目启动成功";
    }
}
