package com.zh.testTransactionInvalid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.testTransactionInvalid.entity.TestUser;

public interface ITestUserService extends IService<TestUser> {
    boolean deleteData();

    boolean testFunc();
}
