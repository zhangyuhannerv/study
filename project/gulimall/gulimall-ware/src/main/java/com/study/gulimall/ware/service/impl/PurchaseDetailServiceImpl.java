package com.study.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.common.utils.PageUtils;
import com.study.common.utils.Query;
import com.study.gulimall.ware.dao.PurchaseDetailDao;
import com.study.gulimall.ware.entity.PurchaseDetailEntity;
import com.study.gulimall.ware.service.PurchaseDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String status = (String) params.get("status");
        String wareId = (String) params.get("wareId");
        String key = (String) params.get("key");

        LambdaQueryWrapper<PurchaseDetailEntity> qw = new LambdaQueryWrapper<>();

        qw.and(StringUtils.isNotBlank(key), item -> {
                    item.eq(PurchaseDetailEntity::getPurchaseId, key)
                            .or()
                            .eq(PurchaseDetailEntity::getSkuId, key);
                }).eq(StringUtils.isNotBlank(status), PurchaseDetailEntity::getStatus, status)
                .eq(StringUtils.isNotBlank(wareId), PurchaseDetailEntity::getWareId, wareId);

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                qw
        );

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> listByPurchaseId(Long id) {
        LambdaQueryWrapper<PurchaseDetailEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(PurchaseDetailEntity::getPurchaseId, id);
        return this.list(qw);
    }

}
