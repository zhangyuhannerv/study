package com.study.springcloud.service;

import java.math.BigDecimal;

public interface IAccountService {
    /**
     * 扣减账户余额
     */
    void decrease(Long userId, BigDecimal money);
}
