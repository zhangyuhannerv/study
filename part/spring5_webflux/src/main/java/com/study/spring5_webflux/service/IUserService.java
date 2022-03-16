package com.study.spring5_webflux.service;

import com.study.spring5_webflux.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// 用户操作接口
public interface IUserService {
    // 根据id查询用户
    // Mono返回0到1个元素
    Mono<User> getUserById(String id);

    // 查询所有用户
    // Flux返回多个元素
    Flux<User> getAllUser();

    // 添加用户
    // Void表示不返回元素
    Mono<Void> saveUser(Mono<User> user);
}
