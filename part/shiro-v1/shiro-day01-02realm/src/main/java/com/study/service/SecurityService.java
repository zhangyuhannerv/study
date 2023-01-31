package com.study.service;

/**
 * 模拟数据库的操作服务接口
 */
public interface SecurityService {
    /**
     * 查找用户名密码
     *
     * @param loginName 用户名称
     * @return 密码
     */
    String findPasswordByLoginName(String loginName);
}
