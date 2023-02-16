package com.itheima.shiro.core.filter;

import com.itheima.shiro.constant.CacheConstant;
import com.itheima.shiro.core.impl.RedisSessionDao;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.ShiroUserUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RDeque;
import org.redisson.api.RedissonClient;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义踢出会话过滤器
 */
@Log4j2
public class KickedOutAuthorizationFilter extends AccessControlFilter {
    private RedissonClient redissonClient;
    private RedisSessionDao redisSessionDao;
    private DefaultWebSessionManager sessionManager;

    public KickedOutAuthorizationFilter(RedissonClient redissonClient, RedisSessionDao redisSessionDao, DefaultWebSessionManager sessionManager) {
        this.redissonClient = redissonClient;
        this.redisSessionDao = redisSessionDao;
        this.sessionManager = sessionManager;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 1.只针对登录用户处理，首先判断是否登录
        Subject subject = getSubject(servletRequest, servletResponse);
        if (!subject.isAuthenticated()) {
            return true;
        }
        // 2.使用redis创建当前用户的队列
        String loginName = ShiroUserUtil.getShiroUser().getLoginName();
        RDeque<String> queue = redissonClient.getDeque(CacheConstant.GROUP_CAS + "KickedOutAuthorizationFilter:" + loginName);
        // 3.判断当前的sessionId是否存在于此用户的队列
        String sessionId = ShiroUserUtil.getShiroSessionId();
        boolean flag = queue.contains(sessionId);
        // 4.不存在则将sessionId放入队列尾
        if (!flag) {
            queue.addLast(sessionId);
        }
        // 5.判断当前队列大小是否超过限定此账号的可在线人数
        // 此处以1为例
        if (queue.size() > 1) {
            // 6 踢出之前登录的session
            // 6.1 取出队列头部的sessionId
            sessionId = queue.getFirst();
            queue.removeFirst();
            // 6.2 根据sessionId拿到session
            Session session = null;
            try {
                session = sessionManager.getSession(new DefaultSessionKey(sessionId));
            } catch (UnknownSessionException e) {
                log.info("session已经失效");
            } catch (ExpiredSessionException e) {
                log.info("session已经过期");
            }
            // 6.3 从sessionDao中移除session会话
            if (!EmptyUtil.isNullOrEmpty(session)) {
                redisSessionDao.delete(session);
            }
        }
        return true;

    }
}
