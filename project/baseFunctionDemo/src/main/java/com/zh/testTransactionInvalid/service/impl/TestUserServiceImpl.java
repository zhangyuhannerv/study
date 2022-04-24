package com.zh.testTransactionInvalid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.testTransactionInvalid.entity.TestUser;
import com.zh.testTransactionInvalid.mapper.TestUserMapper;
import com.zh.testTransactionInvalid.service.ITestUserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.autoproxy.ProxyCreationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName TestUserServiceImpl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/19
 * @Version 1.0
 */
@Service
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser> implements ITestUserService {
    @Override
    @Transactional
    public boolean deleteData() {
        this.removeById("1");
        throw new RuntimeException();
    }

    @Override
    @Transactional
    public boolean testFunc() {
        return this.deleteData();
    }


}
