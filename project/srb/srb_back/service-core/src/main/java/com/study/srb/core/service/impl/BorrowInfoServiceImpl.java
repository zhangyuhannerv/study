package com.study.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.core.mapper.BorrowInfoMapper;
import com.study.srb.core.mapper.IntegralGradeMapper;
import com.study.srb.core.mapper.UserInfoMapper;
import com.study.srb.core.pojo.entity.BorrowInfo;
import com.study.srb.core.pojo.entity.IntegralGrade;
import com.study.srb.core.pojo.entity.UserInfo;
import com.study.srb.core.service.BorrowInfoService;
import com.study.srb.exception.Assert;
import com.study.srb.result.ResponseEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 借款信息表 服务实现类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Service
public class BorrowInfoServiceImpl extends ServiceImpl<BorrowInfoMapper, BorrowInfo> implements BorrowInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private IntegralGradeMapper integralGradeMapper;

    @Override
    public BigDecimal getBorrowAmount(Long userId) {
        // 获取用户积分
        UserInfo userInfo = userInfoMapper.selectById(userId);
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
        Integer integral = userInfo.getIntegral();
        // 根据积分查询额度
        QueryWrapper<IntegralGrade> integralGradeQueryWrapper = new QueryWrapper<>();
        integralGradeQueryWrapper
                .le("integral_start", integral)
                .ge("integral_end", integral);
        IntegralGrade integralGrade = integralGradeMapper.selectOne(integralGradeQueryWrapper);
        if (integralGrade == null) {
            return new BigDecimal("0");
        }

        return integralGrade.getBorrowAmount();
    }
}
