package com.itheima.shiro.core.impl;

import com.itheima.shiro.utils.EmptyUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义会话管理器
 */
public class ShiroSessionManager extends DefaultWebSessionManager {
    // 从请求中获得sessionId的key值
    public static final String AUTHORIZATION = "jwtToken";

    // 字定义注入的资源类型名称
    public static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Override
    public Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 判断request请求中是否带有jwtToken的key
        String jwtToken = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        // 如果没有走默认的cookie获得sessionId的方式
        if (EmptyUtil.isNullOrEmpty(jwtToken)) {
            return super.getSessionId(request, response);
        } else {
            // 如果有判断走jwtToken获得sessionId的方式
            Claims claims = jwtTokenManager.decodeToken(jwtToken);
            String id = (String) claims.get("jti");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }

    }
}
