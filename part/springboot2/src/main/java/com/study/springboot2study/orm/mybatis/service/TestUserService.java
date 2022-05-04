package com.study.springboot2study.orm.mybatis.service;

import com.study.springboot2study.bean.TestUser;
import com.study.springboot2study.orm.mybatis.mapper.TestUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestUserService
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/5
 * @Version 1.0
 */
@Service
public class TestUserService {
    @Autowired
    TestUserMapper testUserMapper;

    public TestUser getById(int id) {
        return testUserMapper.getById(id);
    }
}
