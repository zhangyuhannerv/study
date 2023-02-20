package com.itheima.shiro.core.filter;

import com.alibaba.fastjson.JSONObject;
import com.itheima.shiro.constant.ShiroConstant;
import com.itheima.shiro.core.base.BaseResponse;
import com.itheima.shiro.core.impl.ShiroSessionManager;
import com.itheima.shiro.utils.EmptyUtil;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 自定义jwt的资源校验
 */
public class JwtPermsFilter extends PermissionsAuthorizationFilter {


    /**
     * 访问拒绝时调用
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        // 判断当前的请求头中是否带有jwtToken字符串
        String jwtToken = WebUtils.toHttp(request).getHeader(ShiroSessionManager.AUTHORIZATION);
        // 如果有：返回json的应答
        if (!EmptyUtil.isNullOrEmpty(jwtToken)) {
            BaseResponse baseResponse = new BaseResponse(ShiroConstant.NO_AUTH_CODE, ShiroConstant.NO_AUTH_MESSAGE);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(baseResponse));
        }
        // 如果没有：走原始方式
        return super.onAccessDenied(request, response);

    }
}
