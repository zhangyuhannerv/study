package com.itheima.shiro.core.impl;

import com.itheima.shiro.core.SimpleCacheService;
import com.itheima.shiro.utils.ShiroRedissionSerialize;
import com.itheima.shiro.utils.ShiroUtil;
import org.apache.shiro.cache.Cache;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description：实现缓存管理服务
 */
@Component
public class SimpleCacheServiceImpl implements SimpleCacheService {
    @Autowired
    @Qualifier("redissonClientForShiro")
    private RedissonClient redissonClient;

    @Override
    public void creatCache(String cacheName, Cache<Object, Object> cache) {
        RBucket<Object> bucket = redissonClient.getBucket(cacheName);
        bucket.trySet(ShiroRedissionSerialize.serialize(cache),
                ShiroUtil.getShiroSession().getTimeout(),
                TimeUnit.SECONDS);
    }

    @Override
    public Cache<Object, Object> getCache(String cacheName) {
        RBucket<Object> bucket = redissonClient.getBucket(cacheName);
        return (Cache<Object, Object>) ShiroRedissionSerialize.deserialize((String) bucket.get());
    }

    @Override
    public void removeCache(String cacheName) {
        RBucket<Object> bucket = redissonClient.getBucket(cacheName);
        bucket.delete();
    }

    @Override
    public void updateCache(String cacheName, Cache<Object, Object> cache) {
        RBucket<Object> bucket = redissonClient.getBucket(cacheName);
        bucket.set(ShiroRedissionSerialize.serialize(cache),
                ShiroUtil.getShiroSession().getTimeout(),
                TimeUnit.SECONDS);
    }
}
