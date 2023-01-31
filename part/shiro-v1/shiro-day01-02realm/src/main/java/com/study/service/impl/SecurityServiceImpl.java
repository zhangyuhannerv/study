package com.study.service.impl;

import com.study.service.SecurityService;

/**
 * 模拟数据库的操作服务接口实现
 */
public class SecurityServiceImpl implements SecurityService {
    @Override
    public String findPasswordByLoginName(String loginName) {
        return "123";
    }
}
