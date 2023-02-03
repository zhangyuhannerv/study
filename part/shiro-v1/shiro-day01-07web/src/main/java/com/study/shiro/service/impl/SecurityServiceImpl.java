package com.study.shiro.service.impl;

import com.study.shiro.service.SecurityService;
import com.study.shiro.tools.DigestsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 模拟数据库的操作服务接口实现
 */
public class SecurityServiceImpl implements SecurityService {
    @Override
    public Map<String, String> findPasswordByLoginName(String loginName) {
        return DigestsUtil.entryptPassword("123");
    }

    @Override
    public List<String> findRoleByLoginName(String loginName) {
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("dev");
        return list;
    }

    @Override
    public List<String> findPermissionByLoginName(String loginName) {
        List<String> list = new ArrayList<>();
        list.add("order:add");
        list.add("order:list");
        list.add("order:del");
        return list;
    }
}
