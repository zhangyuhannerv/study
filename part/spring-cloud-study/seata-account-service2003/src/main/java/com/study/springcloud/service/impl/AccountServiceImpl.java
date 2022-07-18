package com.study.springcloud.service.impl;

import com.study.springcloud.dao.IAccountDao;
import com.study.springcloud.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountServiceImpl implements IAccountService {
    @Autowired
    IAccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        // 模拟超时异常
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        accountDao.decrease(userId, money);
    }
}
