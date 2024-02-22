package com.study.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.constant.ProductConstant;
import com.study.common.utils.PageUtils;
import com.study.common.utils.Query;
import com.study.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.study.gulimall.product.dao.AttrDao;
import com.study.gulimall.product.dao.AttrGroupDao;
import com.study.gulimall.product.dao.CategoryDao;
import com.study.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.study.gulimall.product.entity.AttrEntity;
import com.study.gulimall.product.entity.AttrGroupEntity;
import com.study.gulimall.product.entity.CategoryEntity;
import com.study.gulimall.product.service.AttrService;
import com.study.gulimall.product.service.CategoryService;
import com.study.gulimall.product.vo.AttrGroupRelationVo;
import com.study.gulimall.product.vo.AttrRespVo;
import com.study.gulimall.product.vo.AttrVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    @Autowired
    AttrAttrgroupRelationDao relationDao;
    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);

        // 保存基本数据
        this.save(attrEntity);

        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 保存关联关系
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        LambdaQueryWrapper<AttrEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(catelogId != 0, AttrEntity::getCatelogId, catelogId)
                .eq(AttrEntity::getAttrType, "base".equalsIgnoreCase(type) ?
                        ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                        :
                        ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        String key = (String) params.get("key");
        qw.and(StringUtils.isNotBlank(key), wrapper -> {
            wrapper.eq(AttrEntity::getAttrId, key)
                    .or()
                    .like(AttrEntity::getAttrName, key);
        });
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                qw
        );

        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map((attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            if ("base".equalsIgnoreCase(type)) {
                // 设置分类和分组的名字
                // 查询关联的分组
                LambdaQueryWrapper<AttrAttrgroupRelationEntity> relationQw = new LambdaQueryWrapper<>();
                relationQw.eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId());
                AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(relationQw);

                // 查询组
                if (relationEntity != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    if (attrGroupEntity != null) {
                        attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                    }
                }
            }


            // 查询分类
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        })).collect(Collectors.toList());

        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, respVo);

        if (attrEntity.getAttrType().equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())) {

            // 设置分组信息
            LambdaQueryWrapper<AttrAttrgroupRelationEntity> ew = new LambdaQueryWrapper<>();
            ew.eq(AttrAttrgroupRelationEntity::getAttrId, attrId);
            AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(ew);

            if (relationEntity != null) {
                respVo.setAttrGroupId(relationEntity.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                if (attrGroupEntity != null) {
                    respVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }


        // 设置分类信息
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        respVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null) {
            respVo.setCatelogName(categoryEntity.getName());
        }

        return respVo;
    }

    @Override
    @Transactional
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        if (attrEntity.getAttrType().equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())) {
            Integer count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attr.getAttrId()));
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());
            if (count > 0) {
                // 修改分组关联
                relationDao.update(relationEntity,
                        new UpdateWrapper<AttrAttrgroupRelationEntity>()
                                .eq("attr_id", attr.getAttrId()));
            } else {
                relationDao.insert(relationEntity);
            }
        }

    }

    /**
     * 根据分组Id查找所有关联的基本属性
     *
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> ew = new LambdaQueryWrapper<>();
        ew.eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrgroupId);
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(ew);

        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }

        List<Long> attrIds = entities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);
        return (List<AttrEntity>) attrEntities;
    }

    @Override
    public void deleteRelation(List<AttrGroupRelationVo> vos) {
        List<AttrAttrgroupRelationEntity> entities = vos.stream().map(e -> {
            AttrAttrgroupRelationEntity attrRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(e, attrRelationEntity);
            return attrRelationEntity;

        }).collect(Collectors.toList());
        relationDao.deleteBatchRelation(entities);
    }

    /**
     * 获取当前分组没有关联的所有属性
     *
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        // 当前分组只能关联自己所属分类里面的属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        // 当前分组只能关联别的分组没有引用的属性
        // 当前分类下的所有组
        LambdaQueryWrapper<AttrGroupEntity> ew = new LambdaQueryWrapper<>();
        ew.eq(AttrGroupEntity::getCatelogId, catelogId);
        List<AttrGroupEntity> groupEntities = attrGroupDao.selectList(ew);
        List<Long> groupIds = groupEntities.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());

        // 所有分组关联的所有属性
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> relationEw = new LambdaQueryWrapper<>();
        relationEw.in(AttrAttrgroupRelationEntity::getAttrGroupId, groupIds);
        List<AttrAttrgroupRelationEntity> relations = relationDao.selectList(relationEw);
        List<Long> attrIds = relations.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        // 当前分类下的所有属性移除已经关联的属性
        LambdaQueryWrapper<AttrEntity> attrEw = new LambdaQueryWrapper<>();
        attrEw.eq(AttrEntity::getCatelogId, catelogId)
                .eq(AttrEntity::getAttrType, ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())
                .notIn(CollectionUtils.isNotEmpty(attrIds), AttrEntity::getAttrId, attrIds);
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            attrEw.and(e -> {
                e.eq(AttrEntity::getAttrId, key).or().like(AttrEntity::getAttrName, key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), attrEw);

        return new PageUtils(page);
    }

}
