package com.study.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.core.hfb.FormHelper;
import com.study.srb.core.hfb.HfbConst;
import com.study.srb.core.hfb.RequestHelper;
import com.study.srb.core.mapper.UserAccountMapper;
import com.study.srb.core.mapper.UserInfoMapper;
import com.study.srb.core.pojo.entity.UserAccount;
import com.study.srb.core.pojo.entity.UserInfo;
import com.study.srb.core.service.UserAccountService;
import com.study.srb.core.util.LendNoUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public String commitCharge(BigDecimal chargeAmt, Long userId) {
        // 获取用户信息
        UserInfo userInfo = userInfoMapper.selectById(userId);

        // 组装参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentBillNo", LendNoUtils.getChargeNo());
        paramMap.put("bindCode", userInfo.getBindCode());
        paramMap.put("chargeAmt", chargeAmt);
        paramMap.put("feeAmt", new BigDecimal(0));// 充值时商家收取的手续费
        paramMap.put("notifyUrl", HfbConst.RECHARGE_NOTIFY_URL);
        paramMap.put("returnUrl", HfbConst.RECHARGE_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        // 利用参数组装form表单
        return FormHelper.buildForm(HfbConst.RECHARGE_URL, paramMap);
    }
}
