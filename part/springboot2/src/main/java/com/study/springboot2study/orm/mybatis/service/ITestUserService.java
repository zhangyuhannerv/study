package com.study.springboot2study.orm.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.springboot2study.bean.TestUser;

public interface ITestUserService extends IService<TestUser> {
    TestUser getById(int id);

    TestUser getById2(int id);

    TestUser insertTestUser(TestUser testUser);

    TestUser insertTestUser2(TestUser testUser);
}
