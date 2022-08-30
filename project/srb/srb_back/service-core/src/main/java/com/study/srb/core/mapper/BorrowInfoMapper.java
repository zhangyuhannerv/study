package com.study.srb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.srb.core.pojo.entity.BorrowInfo;

import java.util.List;

/**
 * <p>
 * 借款信息表 Mapper 接口
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
public interface BorrowInfoMapper extends BaseMapper<BorrowInfo> {
    List<BorrowInfo> selectBorrowInfoList();
}
