package com.study.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.srb.core.pojo.entity.BorrowInfo;
import com.study.srb.core.pojo.entity.Lend;
import com.study.srb.core.pojo.vo.BorrowInfoApprovalVO;

import java.util.List;

/**
 * <p>
 * 标的准备表 服务类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
public interface LendService extends IService<Lend> {

    void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo);

    List<Lend> selectList();
}
