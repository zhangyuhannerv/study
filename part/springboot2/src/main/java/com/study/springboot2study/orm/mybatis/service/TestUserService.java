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

    public TestUser getById2(int id) {
        return testUserMapper.getById2(id);
    }

    public TestUser insertTestUser(TestUser testUser) {
        testUserMapper.insertTestUser(testUser);
        return testUser;
    }

    public TestUser insertTestUser2(TestUser testUser) {
        testUserMapper.insertTestUser2(testUser);
        return testUser;
    }
}
