package com.study.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.srb.core.pojo.entity.LendReturn;

import java.util.List;

/**
 * <p>
 * 还款记录表 服务类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
public interface LendReturnService extends IService<LendReturn> {

    List<LendReturn> selectByLendId(Long lendId);

    String commitReturn(Long lendReturnId, Long userId);
}
