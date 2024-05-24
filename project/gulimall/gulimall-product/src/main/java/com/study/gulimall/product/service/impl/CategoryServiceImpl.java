package com.study.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.utils.PageUtils;
import com.study.common.utils.Query;
import com.study.gulimall.product.dao.CategoryDao;
import com.study.gulimall.product.entity.CategoryEntity;
import com.study.gulimall.product.service.CategoryBrandRelationService;
import com.study.gulimall.product.service.CategoryService;
import com.study.gulimall.product.vo.Catalog2Vo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedissonClient redisson;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 1.查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        // 2.组装成父子树形结构
        // 2.1找到所有的一级分类
        List<CategoryEntity> level1Menus =
                entities.stream()
                        .filter(e -> e.getParentCid() == 0)
                        .map(e -> {
                            e.setChildren(getChildren(e, entities));
                            return e;
                        })
                        .sorted(Comparator.comparingInt(e -> (e.getSort() == null ? 0 : e.getSort())))
                        .collect(Collectors.toList());


        return level1Menus;

    }

    @Override
    public void removeMenuByIds(List<Long> catIds) {
        //todo 检查当前删除的菜单是否被别的地方引用

        // 逻辑删除
        baseMapper.deleteBatchIds(catIds);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        findParentPath(catelogId, paths);
        Collections.reverse(paths);
        return paths.toArray(new Long[0]);
    }

    /**
     * 级联更新所有关联的数据
     *
     * @param category
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (StringUtils.isNotBlank(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    // 每一个需要缓存的数据我们都要指定要放到哪个名字的缓存【可以理解为：缓存的分区，推荐按照业务类型来分】
    // @Cacheable的默认行为：
    // 缓存中有方法的话就不会调用了
    // key:默认自动生成的,缓存的名字（category）::SimpleKey []
    // value：默认使用jdk序列化机制将序列化后的数据存储到redis
    // ttl:默认时间是-1，永不过期
    //
    // 自定义修改：
    // 修改缓存key：key属性手动指定，接收spel表达式
    // 修改缓存ttl: 通过配置文件里的spring.cache.redis.time-to-live: 3600000，设置过期时间为1小时
    // 修改value为json格式

    // @Cacheable代表当前方法的结果需要缓存，如果缓存中有，那么方法就不调用了；如果缓存中没有，会调用方法，最后将方法的结果放入缓存
    // 手动指定key的名称
//    @Cacheable(value = {"category"}, key = "'Level1Categorys'")
    // 使用方法名称作为key
    @Cacheable(value = {"category"}, key = "#root.method.name")
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        System.out.println("调用方法了");
        LambdaQueryWrapper<CategoryEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(CategoryEntity::getParentCid, 0);
        return baseMapper.selectList(qw);
    }

    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        // 空结果缓存，解决缓存穿透
        // 设置过期时间+随机值，解决缓存雪崩
        // 枷锁，解决缓存击穿问题

        // 放入缓存，缓存中存的所有数据都是json字符串
        // json跨语言，跨平台兼容
        String key = "catalogJSON";
        String catalogJSON = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(catalogJSON)) {
            // 缓存中没有，从数据库中查询
            // 本地锁
//            return getCatalogJsonFromLocalLock();
            // 分布式锁（1阶段,直接使用redis）
//            return getCatalogJsonFromRedisLockLevel1();
            // 分布式锁（2阶段,使用redisson）
            return getCatalogJsonFromRedisLockLevel2();
        }

        // 缓存中有，转为指定的对象

        return JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
        });

    }

    /**
     * 分布式锁（二阶段，使用redisson）
     * 这基本上是最终版了
     * 但是还要考虑一个问题：缓存里的数据如何和数据库保持一致，即缓存一致性的问题
     * 通用的两种解决方案
     * 1）双写模式：改数据库，再改缓存
     * 2）失效模式：改数据库，再删除缓存，等待下次主动查询进行更新，好处多多，用这个
     * 当前系统缓存一致性的解决方案：
     * 1）缓存的所有数据都有过期时间，这样即使有脏数据，当缓存过期后查询的时候也能触发主动更新
     * 2）读写数据的时候，加上分布式的读写锁。不是全加，经常写的数据或对数据一致性要求不高的场景不推荐加，影响性能
     *
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromRedisLockLevel2() {
        // 锁定名字一样，锁就一样。一定要注意锁的名字，不同的业务，要取不同的名字
        // 因此锁的粒度，越细。业务越不会重用同一把锁，系统越快
        // 约定，锁的粒度：
        // 具体缓存某个数据（比如11号商品）：product-11-lock,product-12-lock.....每个商品都有自己的锁，而不是product-lock。锁住所有的商品查询接口
        RLock lock = redisson.getLock("catalogJson-lock");
        lock.lock();
        Map<String, List<Catalog2Vo>> catalogJsonFromDb = null;
        try {
            catalogJsonFromDb = getCatalogJsonFromDb();
        } finally {
            lock.unlock();
        }
        return catalogJsonFromDb;
    }

    /**
     * 分布式锁（一阶段，直接使用redis）
     */

    public Map<String, List<Catalog2Vo>> getCatalogJsonFromRedisLockLevel1() {
        String lockKey = "lock";
        String lockValue = UUID.randomUUID().toString();

        // 加锁，同时给key设置过期时间，防止死锁。设置过期时间和加锁必须是原子操作
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, 300, TimeUnit.SECONDS);
        if (Boolean.TRUE.equals(lock)) {
            // 加锁成功
            Map<String, List<Catalog2Vo>> catalogJsonFromDb = null;
            try {
                // 执行业务
                // 还有一个锁的续期操作，就是业务执行的时间过长，超过锁的过期时间，此时应该给锁续期，加大锁的过期时间
                // 但是续期操作比较难实现，所以，这里不做，直接把锁的过期时间设置很大即可
                catalogJsonFromDb = getCatalogJsonFromDb();
            } catch (Exception e) {
                log.error("业务执行出错，错误信息:{}", e.getMessage());
                // 执行完业务，删除锁
                // 保证删除的锁一定是自己的，而不是别的服务的锁，所以先按照key取出来，判断取出来的value与本线程的value是否相等
                // 获取值，对比成功删除这两步一定要是一个原子操作，所以要结合lua脚本使用
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                // 删除锁（执行上面的lua脚本，这是一个原子操作），不关注结果，删除即可
                Long execResult = stringRedisTemplate.
                        execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList(lockKey), lockValue);
            }
            return catalogJsonFromDb;
        } else {
            // 自旋锁（不断重试）
            try {
                // 没有获得锁，沉睡一段时间之后再执行
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return getCatalogJsonFromRedisLockLevel1();
        }
    }

    /**
     * 本地锁
     *
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromLocalLock() {
        // 一定要保证查数据库，查完数据库放入缓存是一个原子操作
        synchronized (this) {
            return getCatalogJsonFromDb();
        }
    }

    /**
     * 从数据库查询db，封装整个分类数据。一定要保证查数据库，查完数据库放入缓存是一个原子操作
     *
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDb() {
        String key = "catalogJSON";
        if (stringRedisTemplate.hasKey(key)) {
            String catalogJSON = stringRedisTemplate.opsForValue().get(key);
            return JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
            });
        }

        // 在数据库查询所有
        List<CategoryEntity> selectList = baseMapper.selectList(null);

        // 查出所有1级分类
        List<CategoryEntity> level1Categorys = getByParentCid(selectList, 0L);

        // 封装数据
        Map<String, List<Catalog2Vo>> collect = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 查询当前一级分类下的所有二级分类
            List<CategoryEntity> categoryEntities = getByParentCid(selectList, v.getCatId());

            // 封装数据
            List<Catalog2Vo> catalog2Vos = new ArrayList<>();

            if (!categoryEntities.isEmpty()) {
                catalog2Vos = categoryEntities.stream().map(l2 -> {
                    Catalog2Vo catalog2Vo = new Catalog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());

                    // 找当前分类的三级分类封装成vo
                    List<CategoryEntity> level3Catalog = getByParentCid(selectList, l2.getCatId());

                    List<Catalog2Vo.Catalog3Vo> catalog3VoList = new ArrayList<>();
                    if (!level3Catalog.isEmpty()) {
                        catalog3VoList = level3Catalog.stream().map(l3 -> {
                            Catalog2Vo.Catalog3Vo catalog3Vo =
                                    new Catalog2Vo.Catalog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return catalog3Vo;
                        }).collect(Collectors.toList());
                    }

                    catalog2Vo.setCatalog3List(catalog3VoList);

                    return catalog2Vo;
                }).collect(Collectors.toList());
            }

            return catalog2Vos;
        }));

        // 数据库中查到的数据放入缓存
        // 将查出的对象转为json放入缓存
        String jsonString = JSON.toJSONString(collect);
        stringRedisTemplate.opsForValue().set(key, jsonString, 1, TimeUnit.DAYS);
        return collect;
    }

    private List<CategoryEntity> getByParentCid(List<CategoryEntity> selectdList, Long parentCid) {
        return selectdList.stream().filter(item -> item.getParentCid().equals(parentCid)).collect(Collectors.toList());
    }

    private void findParentPath(Long catelogId, List<Long> paths) {
        // 收集当前节点id
        paths.add(catelogId);
        CategoryEntity category = this.getById(catelogId);
        if (category.getParentCid() != 0) {
            findParentPath(category.getParentCid(), paths);
        }
    }


    // 递归查找所有某个菜单的所有层级子菜单
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream()
                .filter(e -> Objects.equals(e.getParentCid(), root.getCatId()))
                .map(e -> {
                    e.setChildren(getChildren(e, all));
                    return e;
                })
                .sorted(Comparator.comparingInt(e -> (e.getSort() == null ? 0 : e.getSort())))
                .collect(Collectors.toList());
        return children;
    }

}
