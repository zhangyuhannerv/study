/*
 * <b>文件名</b>：LoginServiceImpl.java
 *
 * 文件描述：
 *
 *
 * 2017年11月16日  下午4:17:46
 */

package com.itheima.shiro.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.itheima.shiro.constant.ShiroConstant;
import com.itheima.shiro.core.base.BaseResponse;
import com.itheima.shiro.core.base.ShiroUser;
import com.itheima.shiro.core.base.SimpleToken;
import com.itheima.shiro.core.bridge.UserBridgeService;
import com.itheima.shiro.core.impl.JwtTokenManager;
import com.itheima.shiro.service.LoginService;
import com.itheima.shiro.utils.ShiroUserUtil;
import com.itheima.shiro.utils.ShiroUtil;
import com.itheima.shiro.vo.LoginVo;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 登陆业务实现
 */
@Service("loginService")
@Log4j2
public class LoginServiceImpl implements LoginService {

    @Resource(name = "redissonClientForShiro")
    RedissonClient redissonClient;

    @Autowired
    UserBridgeService userBridgeService;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    /* (non-Javadoc)
     * @see LoginService#route(com.yz.commons.vo.LoginVo)
     */
    @Override
    public Map<String, String> route(LoginVo loginVo) throws UnknownAccountException, IncorrectCredentialsException {
        Map<String, String> map = new HashMap<>();
        try {
            SimpleToken token = new SimpleToken(null, loginVo.getLoginName(), loginVo.getPassWord());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            this.loadAuthorityToCache(token);
        } catch (UnknownAccountException ex) {
            log.error("登陆异常:{}", ex);
            throw new UnknownAccountException(ex);
        } catch (IncorrectCredentialsException ex) {
            log.error("登陆异常:{}", ex);
            throw new IncorrectCredentialsException(ex);
        }
        map.put("authorizationKey", ShiroUtil.getShiroSessionId());
        return map;
    }

    @Override
    public BaseResponse routeForJwt(LoginVo loginVo) throws UnknownAccountException, IncorrectCredentialsException {
        SimpleToken simpleToken = new SimpleToken(null, loginVo.getLoginName(), loginVo.getPassWord());
        String jwtToken;
        try {
            // 登录操作
            Subject subject = SecurityUtils.getSubject();
            subject.login(simpleToken);
            // 登录成功后颁发令牌
            ShiroUser shiroUser = ShiroUserUtil.getShiroUser();
            String sessionId = ShiroUserUtil.getShiroSessionId();
            Map<String, Object> claims = new HashMap<>();
            claims.put("shiroUser", JSONObject.toJSONString(shiroUser));
            jwtToken = jwtTokenManager.issuedToken("system", subject.getSession().getTimeout(), sessionId, claims);
        } catch (Exception e) {
            return new BaseResponse(ShiroConstant.LOGIN_FAILURE_CODE, ShiroConstant.LOGIN_FAILURE_MESSAGE);
        }

        // 创建缓存
        this.loadAuthorityToCache(simpleToken);
        return new BaseResponse(ShiroConstant.LOGIN_SUCCESS_CODE, ShiroConstant.LOGIN_SUCCESS_MESSAGE, jwtToken);
    }

    /**
     * <b>方法名：</b>：loadAuthorityToCache<br>
     * <b>功能说明：</b>：加载缓存<br>
     *
     * @param token
     * @author <font color='blue'>束文奇</font>
     * @date 2017年11月16日 下午4:55:04
     */
    private void loadAuthorityToCache(SimpleToken token) {
        //登陆成功后缓存用户的权限信息进入缓存
        ShiroUser shiroUser = ShiroUserUtil.getShiroUser();
        userBridgeService.loadUserAuthorityToCache(shiroUser);

    }

}
