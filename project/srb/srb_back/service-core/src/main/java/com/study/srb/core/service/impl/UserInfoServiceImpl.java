package com.study.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.srb.core.mapper.UserAccountMapper;
import com.study.srb.core.pojo.dto.vo.RegisterVO;
import com.study.srb.core.pojo.entity.UserAccount;
import com.study.srb.core.pojo.entity.UserInfo;
import com.study.srb.core.mapper.UserInfoMapper;
import com.study.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.exception.Assert;
import com.study.srb.result.ResponseEnum;
import com.study.srb.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    private UserAccountMapper userAccountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterVO registerVO) {
        // 判断用户是否已经被注册
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("mobile", registerVO.getMobile());
        Integer count = baseMapper.selectCount(userInfoQueryWrapper);
        Assert.isTrue(count == 0, ResponseEnum.MOBILE_EXIST_ERROR);
        // 插入用户信息：user_info
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(registerVO.getUserType());
        userInfo.setNickName(registerVO.getMobile());
        userInfo.setName(registerVO.getMobile());
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
        userInfo.setStatus(UserInfo.STATUS_NORMAL);
        userInfo.setHeadImg(UserInfo.USER_AVATAR);
        baseMapper.insert(userInfo);
        // 插入用户账户记录:user_account
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        userAccountMapper.insert(userAccount);
    }
}
