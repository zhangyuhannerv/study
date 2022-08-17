package com.study.srb.core.service;

import com.study.srb.core.pojo.entity.UserBind;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.srb.core.pojo.vo.UserBindVo;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
public interface UserBindService extends IService<UserBind> {

    String commitBindUser(UserBindVo userBindVo, Long userId);
}
