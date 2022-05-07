package com.study.springboot2study.thymeleafController;

import com.study.springboot2study.bean.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;

/**
 * @ClassName IndexController
 * @Description 后台框架的基本搭建
 * @Author Zhangyuhan
 * @Date 2022/4/18
 * @Version 1.0
 */
@Controller
@Slf4j
public class IndexController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 登录页
     *
     * @return
     */
    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    /**
     * 登陆验证
     *
     * @return
     */
    @PostMapping("/loginValid")
    public String main(LoginUser user, HttpSession session, Model model) {

        // 只要登陆的用户名和密码不为空，那么就判断登陆成功
        if (StringUtils.hasLength(user.getUsername()) && "123".equals(user.getPassword())) {
            // 登陆成功后把登陆的user存起来
            session.setAttribute("loginUser", user);
            // 登陆成功后重定向到main。重定向是为了在刷新首页的时候不用重复提交表单，而是直接走/main接口
            return "redirect:/main";
        } else {
            // 登陆失败，回到登录
            model.addAttribute("msg", "账号密码错误");
            return "login";
        }
    }

    /**
     * 首页
     * 解决表单重复提交
     */
    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {


        // 没配置拦截器之前的写法
        /*// 前置的判断，是否登陆，后续会引入拦截器/过滤器机制
        if (session.getAttribute("loginUser") != null) {
            return "main";
        } else {
            model.addAttribute("msg", "当前未登陆，请重新登陆");
            return "login";// 返回登陆页面
        }*/


        log.info("handler方法执行");

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String main = operations.get("/main");
        String dynamic_table = operations.get("/dynamic_table");

        model.addAttribute("mainCount", main);
        model.addAttribute("dynamic_tableCount", dynamic_table);

        // 配置拦截器之后的写法
        return "main";


    }
}
