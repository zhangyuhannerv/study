package com.study.srb_mybatis_plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb_mybatis_plus.entity.User;
import com.study.srb_mybatis_plus.mapper.UserMapper;
import com.study.srb_mybatis_plus.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
//    @Resource
//    UserMapper userMapper;

    @Override
    public List<User> listAllByName(String name) {
//        return userMapper.selectAllByName(name);
        return baseMapper.selectAllByName(name);
    }
}
