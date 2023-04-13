package com.itheima.shiro.core.filter;

import com.alibaba.fastjson.JSONObject;
import com.itheima.shiro.constant.ShiroConstant;
import com.itheima.shiro.core.base.BaseResponse;
import com.itheima.shiro.core.impl.JwtTokenManager;
import com.itheima.shiro.core.impl.ShiroSessionManager;
import com.itheima.shiro.utils.EmptyUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义登录验证过滤器
 */
public class JwtAuthcFilter extends FormAuthenticationFilter {
    private JwtTokenManager jwtTokenManager;

    public JwtAuthcFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    /**
     * 是否允许访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 判断当前请求头中是否带有jwtToken的字符串
        String jwtToken = WebUtils.toHttp(request).getHeader(ShiroSessionManager.AUTHORIZATION);
        // 如果有，走jwt校验
        if (!EmptyUtil.isNullOrEmpty(jwtToken)) {
            if (jwtTokenManager.isVerifyToken(jwtToken)) {
                return super.isAccessAllowed(request, response, mappedValue);
            } else {
                return false;
            }
        }
        // 如果没有，走原始校验
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 访问拒绝时调用
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 判断当前的请求头中是否带有jwtToken字符串
        String jwtToken = WebUtils.toHttp(request).getHeader(ShiroSessionManager.AUTHORIZATION);
        // 如果有：返回json的应答
        if (!EmptyUtil.isNullOrEmpty(jwtToken)) {
            BaseResponse baseResponse = new BaseResponse(ShiroConstant.NO_LOGIN_CODE, ShiroConstant.NO_LOGIN_MESSAGE);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(baseResponse));
            return false;
        }
        // 如果没有：走原始方式
        return super.onAccessDenied(request, response);

    }
}