package com.itheima.shiro.core;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.PostConstruct;

/**
 * @Description：自定义realm的抽象类
 */
public abstract class ShiroDbRealm extends AuthorizingRealm {

    /**
     * @param token token对象
     * @return 认证信息
     * @Description 认证方法
     */
    @Override
    protected abstract AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException;

    /**
     * @param principals 令牌对象
     * @return 授权信息
     * @Description 授权方法
     */
    @Override
    protected abstract AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals);


    /**
     * @param
     * @return
     * @Description 自定义密码比较器
     */
    @PostConstruct
    public abstract void initCredentialsMatcher();
}
