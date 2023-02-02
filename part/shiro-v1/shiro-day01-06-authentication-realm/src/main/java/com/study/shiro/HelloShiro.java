package com.study.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * shiro的第一个例子
 */
public class HelloShiro {
    @Test
    public void testPermissionRealm() {
        Subject subject = shiroLogin();
        // 判断用户是否登录成功
        // 打印登录信息
        System.out.println("登录结果：" + subject.isAuthenticated());

        // 校验当前用户是否拥有管理员的角色
        System.out.println("是否有管理员角色：" + subject.hasRole("admin"));
        // 校验当前用户没有的角色
        try {
            subject.checkRole("coder");
            System.out.println("当前用户有coder角色");
        } catch (Exception e) {
            System.out.println("当前用户没有coder角色");
            e.printStackTrace();
        }

        // 校验当前用户的权限信息
        System.out.println("是否有查看订单的权限" + subject.isPermitted("order:list"));
        // 校验当前用户没有的权限
        try {
            subject.checkPermission("order:edit");
            System.out.println("当前用户有修改订单的权限");
        } catch (AuthorizationException e) {
            System.out.println("当前用户没有修改订单的权限");
            e.printStackTrace();
        }

    }


    public Subject shiroLogin() {
        // 导入ini配置创建工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 工厂构建安全管理器
        SecurityManager securityManager = factory.getInstance();
        // 使用工具生效安全管理爱
        SecurityUtils.setSecurityManager(securityManager);
        // 使用工具获得subject主体
        Subject subject = SecurityUtils.getSubject();
        // 构建账户密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jay", "123");
        // 使用subject主体去登录
        subject.login(usernamePasswordToken);
        // 返回主体
        return subject;
    }
}
