package com.study.shiro.service;

import java.util.List;
import java.util.Map;

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
    Map<String, String> findPasswordByLoginName(String loginName);

    /**
     * 查询角色
     *
     * @param loginName 用户名
     * @return 角色字符串
     */
    List<String> findRoleByLoginName(String loginName);

    /**
     * 查询权限
     *
     * @param loginName 用户名
     * @return 权限字符串
     */
    List<String> findPermissionByLoginName(String loginName);
}
