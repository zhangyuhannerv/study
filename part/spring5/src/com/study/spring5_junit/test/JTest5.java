package com.study.spring5_junit.test;

import com.study.spring5_transaction.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @ClassName JTest5
 * @Description spring5整合junit5
 * @Author Zhangyuhan
 * @Date 2022/3/14
 * @Version 1.0
 */
// @ExtendWith(SpringExtension.class)
// @ContextConfiguration("classpath:com/study/spring5_transaction/bean.xml")// 加载配置文件
// 该注解是上面两种注解的复合形式
@SpringJUnitConfig(locations = "classpath:com/study/spring5_transaction/bean.xml")
public class JTest5 {

    @Autowired
    private AccountService accountService;

    @Test
    public void test1() {
        accountService.transferMoney();
    }
}
