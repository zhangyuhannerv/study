package com.itheima.shiro.core;

import org.apache.shiro.cache.Cache;

/**
 * @Description：实现缓存管理服务
 */
public interface SimpleCacheService {

    /**
     * @param cacheName 缓存名称
     * @param cache     缓存对象
     * @return
     * @Description 创建缓存
     */
    void creatCache(String cacheName, Cache<Object, Object> cache);

    /**
     * @param cacheName 缓存名称
     * @return 缓存对象
     * @Description 获得缓存
     */
    Cache<Object, Object> getCache(String cacheName);

    /**
     * @param cacheName 缓存名称
     * @return
     * @Description 删除缓存
     */
    void removeCache(String cacheName);

    /**
     * @param cacheName 缓存名称
     * @param cache     新的缓存对象
     * @return
     * @Description 更新缓存
     */
    void updateCache(String cacheName, Cache<Object, Object> cache);
}
