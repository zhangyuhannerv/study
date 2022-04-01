package com.study.springboot2study.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/31
 * @Version 1.0
 */
@RestController
public class UserController {
    @GetMapping("/user")
    // @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser() {
        return "GET-张三";
    }

    @PostMapping("/user")
    // @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveUser() {
        return "POST-张三";
    }

    @PutMapping("/user")
    // @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String putUser() {
        return "PUT-张三";
    }

    @DeleteMapping("/user")
    // @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public String deleteUser() {
        return "DELETE-张三";
    }
}
