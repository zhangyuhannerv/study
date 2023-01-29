package com.study.websocketspringboot.controller;


import com.study.websocketspringboot.pojo.Result;
import com.study.websocketspringboot.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @RequestMapping("/login")
    public Result login(User user, HttpSession session) {
        Result result = new Result();
        if (user != null && "123".equals(user.getPassword())) {
            result.setFlag(true);
            session.setAttribute("user", user.getUsername());
        } else {
            result.setFlag(false);
            result.setMessage("登录失败");
        }
        return result;
    }

    @RequestMapping("/getUserName")
    public String getUserName(HttpSession session) {
        return String.valueOf(session.getAttribute("user"));
    }
}
