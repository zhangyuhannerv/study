package com.study.spring5_transaction.service;

import com.study.spring5_transaction.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName AccountService
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/9
 * @Version 1.0
 */
@Service

// 该注解既可以加到类上面，也可以加到方法上面
// 如果添加到类上面，那么该类下所有的方法都添加了事务
// 如果添加到方法上面，那么只是为该方法添加事务
@Transactional
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    // 转账的方法
    public void transferMoney() {
        accountDao.reduceMoney();// 余额减少

        // 模拟异常
        int i = 10 / 0;

        accountDao.addMoney();// 余额增加
    }
}
