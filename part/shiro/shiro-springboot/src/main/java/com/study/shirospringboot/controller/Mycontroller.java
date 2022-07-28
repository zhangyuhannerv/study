package com.study.shirospringboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName Mycontroller
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/14
 * @Version 1.0
 */

@Controller
public class Mycontroller {
    @RequestMapping({"/", "/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "hello,shiro");
        // Subject subject = SecurityUtils.getSubject();
        // Session session = subject.getSession();
        // System.out.println(session.getAttribute("loginUser"));
        return "index";
    }

    @RequestMapping("/user/add")
    public String toAdd() {
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String toUpdate() {
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登陆数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token); // 执行登录的方法，如果没有异常，说明可以登录成功

            return "index";
        } catch (UnknownAccountException e) {// 用户名不存在异常
            model.addAttribute("msg", "用户名不存在异常");
            return "login";
        } catch (IncorrectCredentialsException e) {// 密码不存在
            model.addAttribute("msg", "密码错误");
            return "login";
        }

    }

    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized() {
        return "未经授权无法访问此页面";
    }
}
