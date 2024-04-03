package com.study.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.utils.PageUtils;
import com.study.common.utils.Query;
import com.study.gulimall.ware.dao.WareInfoDao;
import com.study.gulimall.ware.entity.WareInfoEntity;
import com.study.gulimall.ware.service.WareInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<WareInfoEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtils.isNotBlank(key), WareInfoEntity::getId, key)
                .or(StringUtils.isNotBlank(key))
                .like(StringUtils.isNotBlank(key), WareInfoEntity::getName, key)
                .or(StringUtils.isNotBlank(key))
                .like(StringUtils.isNotBlank(key), WareInfoEntity::getAddress, key)
                .or(StringUtils.isNotBlank(key))
                .like(StringUtils.isNotBlank(key), WareInfoEntity::getAreacode, key);
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                qw
        );

        return new PageUtils(page);
    }

}
