package com.study.shiro.realm;

import com.study.shiro.service.SecurityService;
import com.study.shiro.service.impl.SecurityServiceImpl;
import com.study.shiro.tools.DigestsUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Map;

public class DefinitionRealm extends AuthorizingRealm {

    /**
     * 更新密码比较器为密文的密码比较器
     */
    public DefinitionRealm() {
        // 指定密码匹配方式 sha1
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher(DigestsUtil.SHA1);
        // 指定密码迭代次数
        hashedCredentialsMatcher.setHashIterations(DigestsUtil.ITERATIONS);
        // 使用父层方法使匹配方式生效
        setCredentialsMatcher(hashedCredentialsMatcher);

    }

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
        Map<String, String> map = securityService.findPasswordByLoginName(loginName);
        if (map.isEmpty()) {
            throw new UnknownAccountException("账户不存在");
        }
        String salt = map.get("salt");
        String password = map.get("password");
        return new SimpleAuthenticationInfo(loginName, password, ByteSource.Util.bytes(salt), getName());
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
