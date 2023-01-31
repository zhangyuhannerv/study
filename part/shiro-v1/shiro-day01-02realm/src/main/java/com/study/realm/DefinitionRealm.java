package com.study.realm;

import com.study.service.SecurityService;
import com.study.service.impl.SecurityServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class DefinitionRealm extends AuthorizingRealm {
    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取登录名
        String loginName = (String) authenticationToken.getPrincipal();
        SecurityService securityService = new SecurityServiceImpl();
        String password = securityService.findPasswordByLoginName(loginName);
        if ("".equals(password)) {
            throw new UnknownAccountException("账户不存在");
        }
        return new SimpleAuthenticationInfo(loginName, password, getName());
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
