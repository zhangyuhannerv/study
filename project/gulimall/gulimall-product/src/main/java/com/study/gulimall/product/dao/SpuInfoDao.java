package com.study.gulimall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.gulimall.product.entity.SpuInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu信息
 *
 * @author ZhangYuhan
 * @email zhangyuhannerv@gmail.com
 * @date 2023-12-08 17:06:12
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    int updateSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
}
