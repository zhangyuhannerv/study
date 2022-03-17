package com.study.spring5_webflux.controller;

import com.study.spring5_webflux.entity.User;
import com.study.spring5_webflux.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/16
 * @Version 1.0
 */
@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    // id查询
    @GetMapping("/user/{id}")
    @ResponseBody
    public Mono<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // 查询所有
    @GetMapping("/getUsers")
    @ResponseBody
    public Flux<User> user() {
        return userService.getAllUser();
    }

    // 添加
    @PostMapping("/saveUser")
    @ResponseBody
    public Mono<Void> saveUser(@RequestBody User user) {
        Mono<User> userMono = Mono.just(user);
        return userService.saveUser(userMono);
    }

}
