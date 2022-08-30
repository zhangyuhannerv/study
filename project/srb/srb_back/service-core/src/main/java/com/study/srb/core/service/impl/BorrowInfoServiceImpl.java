package com.study.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.core.enums.BorrowInfoStatusEnum;
import com.study.srb.core.enums.BorrowerStatusEnum;
import com.study.srb.core.enums.UserBindEnum;
import com.study.srb.core.mapper.BorrowInfoMapper;
import com.study.srb.core.mapper.IntegralGradeMapper;
import com.study.srb.core.mapper.UserInfoMapper;
import com.study.srb.core.pojo.entity.BorrowInfo;
import com.study.srb.core.pojo.entity.IntegralGrade;
import com.study.srb.core.pojo.entity.UserInfo;
import com.study.srb.core.service.BorrowInfoService;
import com.study.srb.core.service.DictService;
import com.study.srb.exception.Assert;
import com.study.srb.result.ResponseEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    @Resource
    private DictService dictService;

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

    @Override
    public void saveBorrowInfo(BorrowInfo borrowInfo, Long userId) {
        // 获取userInfo信息
        UserInfo userInfo = userInfoMapper.selectById(userId);

        // 判断用户绑定状态
        Assert.isTrue(Objects.equals(userInfo.getBindStatus(), UserBindEnum.BIND_OK.getStatus()),
                ResponseEnum.USER_NO_BIND_ERROR);

        // 判断借款人额度申请状态
        Assert.isTrue(Objects.equals(userInfo.getBorrowAuthStatus(), BorrowerStatusEnum.AUTH_OK.getStatus()),
                ResponseEnum.USER_NO_AMOUNT_ERROR);

        // 判断借款人额度是否充足
        BigDecimal borrowAmount = this.getBorrowAmount(userId);
        Assert.isTrue(borrowInfo.getAmount().compareTo(borrowAmount) <= 0,
                ResponseEnum.USER_AMOUNT_LESS_ERROR);

        // 存储borrowInfo数据
        borrowInfo.setUserId(userId);
        // 年化利率比百分比转小数
        BigDecimal borrowYearRate = borrowInfo.getBorrowYearRate();
        borrowInfo.setBorrowYearRate(borrowYearRate.divide(new BigDecimal(100)));
        // 设置借款申请的审核状态
        borrowInfo.setStatus(BorrowInfoStatusEnum.CHECK_RUN.getStatus());
        baseMapper.insert(borrowInfo);
    }

    @Override
    public Integer getStatusByUserId(Long userId) {
        QueryWrapper<BorrowInfo> borrowInfoQueryWrapper = new QueryWrapper<>();
        borrowInfoQueryWrapper.select("status").eq("user_id", userId);
        List<Object> objects = baseMapper.selectObjs(borrowInfoQueryWrapper);
        if (objects.isEmpty()) {
            return BorrowInfoStatusEnum.NO_AUTH.getStatus();
        }

        return (Integer) objects.get(0);
    }

    @Override
    public List<BorrowInfo> selectList() {
        List<BorrowInfo> borrowInfos = baseMapper.selectBorrowInfoList();
        for (BorrowInfo borrowInfo : borrowInfos) {
            String returnMethod =
                    dictService.getNameByParentDictCodeAndValue("returnMethod", borrowInfo.getReturnMethod());

            String moneyUse =
                    dictService.getNameByParentDictCodeAndValue("moneyUse", borrowInfo.getMoneyUse());

            String status = BorrowInfoStatusEnum.getMsgByStatus(borrowInfo.getStatus());

            borrowInfo.getParam().put("returnMethod", returnMethod);
            borrowInfo.getParam().put("moneyUse", moneyUse);
            borrowInfo.getParam().put("status", status);
        }
        return borrowInfos;
    }
}
