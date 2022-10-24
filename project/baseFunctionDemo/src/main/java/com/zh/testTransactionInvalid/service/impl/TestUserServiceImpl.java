package com.zh.testTransactionInvalid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.testTransactionInvalid.entity.TestUser;
import com.zh.testTransactionInvalid.mapper.TestUserMapper;
import com.zh.testTransactionInvalid.service.ITestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Autowired
    @Lazy
    ITestUserService testUserService;

    @Override
    @Transactional
    public void deleteData() {
        TestUser testUser = new TestUser();
        testUser.setId(1L);
        testUser.setAge(19);
        baseMapper.updateById(testUser);
        int a = 1 / 0;
    }

    @Override
//    @Transactional
    public void testFunc() {
        TestUser testUser = new TestUser();
        testUser.setId(2L);
        testUser.setAge(21);
        baseMapper.updateById(testUser);

//        this.deleteData();
        testUserService.deleteData();

    }

    // 总结：
    // 1。一个无事物的方法通过this调用了另一个有事物的方法。
    // 此时。有事物的方法的事物失效。两种更新都不会滚
    // 2。注入自身实例（加入@Lazy，防止依赖循环的问题）在无事物的方法内部通过注入的实例调用另一个有事物的方法
    // 此时。有事物的方法的事物生效，内部的更新回滚。而无事物的方法所做的更新仍不会滚
    // 3。如果两个方法都有事物注解。那么通过this调用或者通过注入自身的实例调用效果一样，无影响。
    // 即发生事物。两个更新都会回滚
}
