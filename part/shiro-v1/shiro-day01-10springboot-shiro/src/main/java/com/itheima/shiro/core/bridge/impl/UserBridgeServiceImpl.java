package com.itheima.shiro.core.bridge.impl;

import com.itheima.shiro.core.adapter.UserAdapter;
import com.itheima.shiro.core.base.ShiroUser;
import com.itheima.shiro.core.bridge.UserBridgeService;
import com.itheima.shiro.pojo.Resource;
import com.itheima.shiro.pojo.Role;
import com.itheima.shiro.pojo.User;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description：用户信息桥接（后期会做缓存）
 */
@Component("userBridgeService")
public class UserBridgeServiceImpl implements UserBridgeService {
    @Autowired
    private UserAdapter userAdapter;

    @Override
    public User findUserByLoginName(String loginName) {
        return userAdapter.findUserByLoginName(loginName);
    }

    @Override
    public List<String> findResourceIds(String userId) {
        List<Resource> resources = userAdapter.findResourceByUserId(userId);
        return resources.parallelStream().map(Resource::getId).collect(Collectors.toList());
    }

    @Override
    public AuthorizationInfo getAuthorizationInfo(ShiroUser shiroUser) {
        // 查询用户对应的角色标识
        List<String> roleList = findRoleList(shiroUser.getId());
        // 查询用户对应的资源标识
        List<String> resourceList = findResourceList(shiroUser.getId());
        // 构建鉴权信息对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleList);
        simpleAuthorizationInfo.addStringPermissions(resourceList);

        return simpleAuthorizationInfo;
    }

    @Override
    public List<String> findRoleList(String userId) {
        List<Role> roles = userAdapter.findRoleByUserId(userId);
        return roles.parallelStream().map(Role::getLabel).collect(Collectors.toList());
    }

    @Override
    public List<String> findResourceList(String userId) {
        List<Resource> resources = userAdapter.findResourceByUserId(userId);
        return resources.parallelStream().map(Resource::getLabel).collect(Collectors.toList());
    }
}
