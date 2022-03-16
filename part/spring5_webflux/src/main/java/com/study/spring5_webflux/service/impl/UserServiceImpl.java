package com.study.spring5_webflux.service.impl;

import com.study.spring5_webflux.entity.User;
import com.study.spring5_webflux.service.IUserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/16
 * @Version 1.0
 */
public class UserServiceImpl implements IUserService {
    // 创建Map集合模拟数据库
    private final Map<Integer, User> users = new HashMap<>();

    {
        users.put(1, new User("lucy", "female", 20));
        users.put(2, new User("mary", "male", 30));
        users.put(3, new User("jack", "male", 50));
    }

    // 查询一个
    @Override
    public Mono<User> getUserById(String id) {
        return Mono.justOrEmpty(this.users.get(id));
    }


    // 查询多个
    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(this.users.values());
    }

    // 添加用户，不返回元素
    @Override
    public Mono<Void> saveUser(Mono<User> user) {
        return user.doOnNext(person -> {
            // 向users里放值
            int id = users.size() + 1;
            users.put(id, person);
        }).thenEmpty(Mono.empty());
        // 最后清空元素，发送一个终止信号
    }
}
