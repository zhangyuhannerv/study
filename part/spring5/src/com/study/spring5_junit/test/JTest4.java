package com.study.spring5_junit.test;

import com.study.spring5_transaction.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName JTest4
 * @Description spring5整合junit4
 * @Author Zhangyuhan
 * @Date 2022/3/14
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)// 指定单元测试的框架的版本
@ContextConfiguration("classpath:com/study/spring5_transaction/bean.xml")// 加载配置文件
public class JTest4 {
    @Autowired
    private AccountService accountService;

    @Test
    public void test1() {
        accountService.transferMoney();
    }
}
