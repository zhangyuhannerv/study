package com.study.shirospringboot.service;

import com.study.shirospringboot.pojo.User;

public interface IUserService {
    public User queryUserByName(String name);
}
