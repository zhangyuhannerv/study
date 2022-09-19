package com.study.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.base.dto.SmsDTO;
import com.study.srb.core.enums.TransTypeEnum;
import com.study.srb.core.hfb.FormHelper;
import com.study.srb.core.hfb.HfbConst;
import com.study.srb.core.hfb.RequestHelper;
import com.study.srb.core.mapper.UserAccountMapper;
import com.study.srb.core.mapper.UserInfoMapper;
import com.study.srb.core.pojo.bo.TransFlowBO;
import com.study.srb.core.pojo.entity.UserAccount;
import com.study.srb.core.pojo.entity.UserInfo;
import com.study.srb.core.service.TransFlowService;
import com.study.srb.core.service.UserAccountService;
import com.study.srb.core.service.UserBindService;
import com.study.srb.core.service.UserInfoService;
import com.study.srb.core.util.LendNoUtils;
import com.study.srb.exception.Assert;
import com.study.srb.rabbitmq.constant.MQConst;
import com.study.srb.rabbitmq.service.MQService;
import com.study.srb.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private TransFlowService transFlowService;

    @Resource
    private UserBindService userBindService;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private MQService mqService;


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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String notify(Map<String, Object> paramMap) {
        // 幂等性判断
        // 标准：判断交易流水是否存在
        String agentBillNo = (String) paramMap.get("agentBillNo");
        boolean isSave = transFlowService.isSaveTransFlow(agentBillNo);
        if (isSave) {
            log.warn("幂等性返回");
            return "success";
        }

        // 账户处理
        String bindCode = (String) paramMap.get("bindCode");
        String chargeAmt = (String) paramMap.get("chargeAmt");
        baseMapper.updateAccount(bindCode, new BigDecimal(chargeAmt), new BigDecimal(0));

        // 记录账户流水
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
                new BigDecimal(chargeAmt),
                TransTypeEnum.RECHARGE,
                "充值"
        );
        transFlowService.saveTransFlow(transFlowBO);

        // 发消息
        String mobile = userInfoService.getMobileByBindCode(bindCode);
        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setMobile(mobile);
        smsDTO.setMessage("充值成功");
        mqService.sendMessage(MQConst.EXCHANGE_TOPIC_SMS, MQConst.ROUTING_SMS_ITEM, smsDTO);
        return "success";
    }

    @Override
    public BigDecimal getAccount(Long userId) {
        QueryWrapper<UserAccount> userAccountQueryWrapper = new QueryWrapper<>();
        userAccountQueryWrapper.eq("user_id", userId);
        UserAccount userAccount = baseMapper.selectOne(userAccountQueryWrapper);

        return userAccount.getAmount();
    }

    @Override
    public String commitWithdraw(BigDecimal fetchAmt, Long userId) {
        // 账户余额
        BigDecimal amount = userAccountService.getAccount(userId);
        Assert.isTrue(amount.compareTo(fetchAmt) >= 0, ResponseEnum.NOT_SUFFICIENT_FUNDS_ERROR);

        String bindCode = userBindService.getBindCodeByUserId(userId);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentBillNo", LendNoUtils.getWithdrawNo());
        paramMap.put("bindCode", bindCode);
        paramMap.put("fetchAmt", fetchAmt);
        paramMap.put("feeAmt", new BigDecimal(0));
        paramMap.put("notifyUrl", HfbConst.WITHDRAW_NOTIFY_URL);
        paramMap.put("returnUrl", HfbConst.WITHDRAW_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        String sign = RequestHelper.getSign(paramMap);
        paramMap.put("sign", sign);

        //构建自动提交表单
        String formStr = FormHelper.buildForm(HfbConst.WITHDRAW_URL, paramMap);
        return formStr;
    }

    @Override
    public void notifyWithdraw(Map<String, Object> paramMap) {
        // 幂等性判断
        log.info("提现成功");
        String agentBillNo = (String) paramMap.get("agentBillNo");
        boolean result = transFlowService.isSaveTransFlow(agentBillNo);
        if (result) {
            log.warn("幂等性返回");
            return;
        }

        // 账户同步
        String bindCode = paramMap.get("bindCode").toString();
        String fetchAmt = (String) paramMap.get("fetchAmt");
        baseMapper.updateAccount(bindCode, new BigDecimal(fetchAmt).negate(), new BigDecimal(0));

        // 交易流水
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
                new BigDecimal(fetchAmt),
                TransTypeEnum.WITHDRAW,
                "提现"
        );
        transFlowService.saveTransFlow(transFlowBO);
    }
}
