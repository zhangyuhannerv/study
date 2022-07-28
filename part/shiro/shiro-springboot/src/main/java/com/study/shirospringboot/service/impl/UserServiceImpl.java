package com.study.shirospringboot.service.impl;

import com.study.shirospringboot.mapper.IUserMapper;
import com.study.shirospringboot.pojo.User;
import com.study.shirospringboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/14
 * @Version 1.0
 */

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserMapper userMapper;

    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }
}
