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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
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
            // 分布式锁（1阶段）
            return getCatalogJsonFromRedisLockLevel1();
        }

        // 缓存中有，转为指定的对象

        return JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
        });

    }

    /**
     * 分布式锁（一阶段）
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
