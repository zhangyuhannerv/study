package com.study.shirospringboot.config;

import com.study.shirospringboot.pojo.User;
import com.study.shirospringboot.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName UserRealm
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/14
 * @Version 1.0
 */

// 自定义的userrealm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    IUserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 执行下面的一行代码就会对所有用户都进行给予一个user：add的授权
        // info.addStringPermission("user:add");
        // 拿到当前登陆的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal(); // 拿到user对象
        // 设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证");


        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        // 正常是数据库中取数据
        // 链接真实数据库
        User user = userService.queryUserByName(userToken.getUsername());

        // 用户名认证
       /* if (!userToken.getUsername().equals(name)) {
            return null; // 抛出异常那 UnknownAccountException
        }*/

        if (user == null) {
            return null;
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser", user);
        // System.out.println(((User) session.getAttribute("loginUser")).toString());

        // 密码可以加密：MD5加密，MD5盐值加密
        // 密码认证
        // 第一个参数传递user就可以在授权方法里拿到改user
        return new SimpleAuthenticationInfo(user, user.getPwd(), "");
    }
}
