/*
 * <b>文件名</b>：LoginService.java
 *
 * 文件描述：
 *
 *
 * 2017年11月16日  下午4:14:05
 */

package com.itheima.shiro.service;

import com.itheima.shiro.core.base.BaseResponse;
import com.itheima.shiro.vo.LoginVo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;

import java.util.Map;

/**
 * @Description 登陆业务接口
 */

public interface LoginService {

    /**
     * @param
     * @return
     * @Description 登陆路由
     */
    public Map<String, String> route(LoginVo loginVo) throws UnknownAccountException, IncorrectCredentialsException;

    BaseResponse routeForJwt(LoginVo loginVo) throws UnknownAccountException, IncorrectCredentialsException;
}
