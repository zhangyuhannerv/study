package com.study.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.study.gulimall.product.vo.AttrRespVo;
import com.study.gulimall.product.vo.AttrVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // 保存关联关系
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attr.getAttrGroupId());
        relationEntity.setAttrId(attrEntity.getAttrId());
        relationDao.insert(relationEntity);
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId) {
        LambdaQueryWrapper<AttrEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(catelogId != 0, AttrEntity::getCatelogId, catelogId);
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

            // 设置分类和分组的名字
            // 查询关联的分组
            LambdaQueryWrapper<AttrAttrgroupRelationEntity> relationQw = new LambdaQueryWrapper<>();
            relationQw.eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId());
            AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(relationQw);

            // 查询组
            if (relationEntity != null) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
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

}
