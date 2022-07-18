package com.study.srb_mybatis_plus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.srb_mybatis_plus.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> listAllByName(String name);
}
