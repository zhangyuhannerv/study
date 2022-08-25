package com.study.srb.core.service;

import com.study.srb.core.pojo.entity.BorrowerAttach;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.srb.core.pojo.vo.BorrowerAttachVO;

import java.util.List;

/**
 * <p>
 * 借款人上传资源表 服务类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
public interface BorrowerAttachService extends IService<BorrowerAttach> {
    List<BorrowerAttachVO> selectBorrowerAttachVOList(Long borrowerId);
}
