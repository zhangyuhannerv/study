package com.study.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.srb.core.pojo.entity.UserInfo;
import com.study.srb.core.pojo.query.UserInfoQuery;
import com.study.srb.core.pojo.vo.LoginVo;
import com.study.srb.core.pojo.vo.RegisterVO;
import com.study.srb.core.pojo.vo.UserIndexVO;
import com.study.srb.core.pojo.vo.UserInfoVO;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
public interface UserInfoService extends IService<UserInfo> {

    void register(RegisterVO registerVO);

    UserInfoVO login(LoginVo loginVo, String ip);

    IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery);

    void lock(Long id, Integer status);

    boolean check(String mobile);

    UserIndexVO getIndexUserInfo(Long userId);

    String getMobileByBindCode(String bindCode);
}
