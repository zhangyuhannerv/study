package com.study.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.srb.core.pojo.entity.BorrowInfo;

import java.math.BigDecimal;

/**
 * <p>
 * 借款信息表 服务类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
public interface BorrowInfoService extends IService<BorrowInfo> {

    BigDecimal getBorrowAmount(Long userId);

    void saveBorrowInfo(BorrowInfo borrowInfo, Long userId);
}
