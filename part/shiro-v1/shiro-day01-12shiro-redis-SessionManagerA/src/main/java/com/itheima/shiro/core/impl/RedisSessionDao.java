package com.itheima.shiro.core.impl;

import com.itheima.shiro.constant.CacheConstant;
import com.itheima.shiro.utils.ShiroRedissionSerialize;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @Description：自定义统一sessiondao实现
 */
public class RedisSessionDao extends AbstractSessionDAO {

    @Autowired
    @Qualifier("redissonClientForShiro")
    private RedissonClient redissonClient;

    private long globalSessionTimeout;

    /**
     * @param session 会话对象
     * @return
     * @Description 创建session
     */
    @Override
    protected Serializable doCreate(Session session) {
        // 创建唯一标识的sessionId
        Serializable sessionId = generateSessionId(session);
        // 为session会话指定唯一的SessionId
        assignSessionId(session, sessionId);
        // 放入缓存中
        String key = CacheConstant.GROUP_CAS + sessionId.toString();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.trySet(ShiroRedissionSerialize.serialize(session), globalSessionTimeout, TimeUnit.MILLISECONDS);
        return sessionId;
    }

    /**
     * @param sessionId 唯一标识
     * @return
     * @Description 读取sessio
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        String key = CacheConstant.GROUP_CAS + sessionId.toString();
        RBucket<String> bucket = redissonClient.getBucket(key);
        return (Session) ShiroRedissionSerialize.deserialize(bucket.get());
    }

    /**
     * @param session 对象
     * @return
     * @Description 更新session
     */
    @Override
    public void update(Session session) throws UnknownSessionException {
        String key = CacheConstant.GROUP_CAS + session.getId().toString();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(ShiroRedissionSerialize.serialize(session), globalSessionTimeout, TimeUnit.MILLISECONDS);
    }

    /**
     * @param
     * @return
     * @Description 删除session
     */
    @Override
    public void delete(Session session) {
        String key = CacheConstant.GROUP_CAS + session.getId().toString();
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.delete();
    }

    /**
     * @param
     * @return
     * @Description 统计当前活跃用户数(后续扩展)
     */
    @Override
    public Collection<Session> getActiveSessions() {
        // 暂时不做扩展，返回空的集合
        return Collections.emptySortedSet();
    }

    public void setGlobalSessionTimeout(long globalSessionTimeout) {
        this.globalSessionTimeout = globalSessionTimeout;
    }
}
