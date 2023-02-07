package com.itheima.shiro.core.bridge;

import com.itheima.shiro.core.base.ShiroUser;
import com.itheima.shiro.pojo.User;
import org.apache.shiro.authz.AuthorizationInfo;

import java.util.List;

/**
 * @Description：用户信息桥接（后期会做缓存）
 */
public interface UserBridgeService {
    /**
     * 查找用户信息
     *
     * @param loginName 用户名
     * @return
     */
    User findUserByLoginName(String loginName);

    /**
     * 查询资源id
     *
     * @param userId 用户id
     * @return 返回资源id集合
     */
    List<String> findResourceIds(String userId);

    /**
     * 鉴权方法
     *
     * @param shiroUser 令牌对象
     * @return 鉴权信息
     */
    AuthorizationInfo getAuthorizationInfo(ShiroUser shiroUser);

    /**
     * 查询用户对应的角色标识
     *
     * @param userId 用户id
     * @return 角色标识集合
     */
    List<String> findRoleList(String userId);

    /**
     * 查询用户对应的资源标识
     *
     * @param userId 用户id
     * @return 资源标识集合
     */
    List<String> findResourceList(String userId);
}
