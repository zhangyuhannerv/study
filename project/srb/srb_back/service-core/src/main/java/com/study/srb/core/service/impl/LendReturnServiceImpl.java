package com.study.srb.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.core.enums.LendStatusEnum;
import com.study.srb.core.enums.TransTypeEnum;
import com.study.srb.core.hfb.FormHelper;
import com.study.srb.core.hfb.HfbConst;
import com.study.srb.core.hfb.RequestHelper;
import com.study.srb.core.mapper.*;
import com.study.srb.core.pojo.bo.TransFlowBO;
import com.study.srb.core.pojo.entity.Lend;
import com.study.srb.core.pojo.entity.LendItem;
import com.study.srb.core.pojo.entity.LendItemReturn;
import com.study.srb.core.pojo.entity.LendReturn;
import com.study.srb.core.service.*;
import com.study.srb.core.util.LendNoUtils;
import com.study.srb.exception.Assert;
import com.study.srb.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 还款记录表 服务实现类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Service
@Slf4j
public class LendReturnServiceImpl extends ServiceImpl<LendReturnMapper, LendReturn> implements LendReturnService {

    @Resource
    private LendMapper lendMapper;

    @Resource
    private UserBindService userBindService;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private LendItemReturnService lendItemReturnService;

    @Resource
    private TransFlowService transFlowService;

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private LendItemReturnMapper lendItemReturnMapper;

    @Resource
    private LendItemMapper lendItemMapper;

    @Override
    public List<LendReturn> selectByLendId(Long lendId) {
        QueryWrapper<LendReturn> queryWrapper = new QueryWrapper();
        queryWrapper.eq("lend_id", lendId);
        List<LendReturn> lendReturnList = baseMapper.selectList(queryWrapper);
        return lendReturnList;
    }

    @Override
    public String commitReturn(Long lendReturnId, Long userId) {
        // 还款记录
        LendReturn lendReturn = baseMapper.selectById(lendReturnId);

        // 获取用户余额
        BigDecimal amount = userAccountService.getAccount(userId);
        Assert.isTrue(amount.compareTo(lendReturn.getTotal()) >= 0,
                ResponseEnum.NOT_SUFFICIENT_FUNDS_ERROR);

        // 标的记录
        Lend lend = lendMapper.selectById(lendReturn.getLendId());
        // 获取还款人的绑定协议号
        String bindCode = userBindService.getBindCodeByUserId(userId);
        // 组装参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        //商户商品名称
        paramMap.put("agentGoodsName", lend.getTitle());
        //批次号
        paramMap.put("agentBatchNo", lendReturn.getReturnNo());
        //还款人绑定协议号
        paramMap.put("fromBindCode", bindCode);
        //还款总额
        paramMap.put("totalAmt", lendReturn.getTotal());
        paramMap.put("note", "");
        //还款明细
        List<Map<String, Object>> lendItemReturnDetailList = lendItemReturnService.addReturnDetail(lendReturnId);
        paramMap.put("data", JSON.toJSONString(lendItemReturnDetailList));
        paramMap.put("voteFeeAmt", new BigDecimal(0));
        paramMap.put("notifyUrl", HfbConst.BORROW_RETURN_NOTIFY_URL);
        paramMap.put("returnUrl", HfbConst.BORROW_RETURN_RETURN_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        String sign = RequestHelper.getSign(paramMap);
        paramMap.put("sign", sign);

        //构建自动提交表单
        String formStr = FormHelper.buildForm(HfbConst.BORROW_RETURN_URL, paramMap);
        return formStr;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notify(Map<String, Object> paramMap) {
        log.info("还款成功");
        // 幂等性判断
        // 还款编号
        String agentBatchNo = (String) paramMap.get("agentBatchNo");

        boolean result = transFlowService.isSaveTransFlow(agentBatchNo);
        if (result) {
            log.warn("幂等性返回");
            return;
        }
        // 获取还款数据
        QueryWrapper<LendReturn> lendReturnQueryWrapper = new QueryWrapper<>();
        lendReturnQueryWrapper.eq("return_no", agentBatchNo);
        LendReturn lendReturn = baseMapper.selectOne(lendReturnQueryWrapper);

        // 更新还款状态
        String voteFeeAmt = (String) paramMap.get("voteFeeAmt");
        lendReturn.setStatus(1);
        lendReturn.setFee(new BigDecimal(voteFeeAmt));
        lendReturn.setRealReturnTime(LocalDateTime.now());
        baseMapper.updateById(lendReturn);

        // 更新标的信息
        Lend lend = lendMapper.selectById(lendReturn.getLendId());
        // 如果是最后一次还款，那么就更新标的状态
        if (Boolean.TRUE.equals(lendReturn.getLast())) {
            lend.setStatus(LendStatusEnum.PAY_OK.getStatus());
            lendMapper.updateById(lend);
        }

        // 还款账号转出金额
        String bindCode = userBindService.getBindCodeByUserId(lendReturn.getUserId());
        BigDecimal totalAmt = new BigDecimal((String) paramMap.get("totalAmt"));
        userAccountMapper.updateAccount(bindCode, totalAmt.negate(), new BigDecimal(0));

        // 还款流水
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBatchNo,
                bindCode,
                totalAmt,
                TransTypeEnum.RETURN_DOWN,
                "借款人还款扣减，项目编号：" + lend.getLendNo() + "，项目名称：" + lend.getTitle());
        transFlowService.saveTransFlow(transFlowBO);

        // 回款明细的获取
        List<LendItemReturn> lendItemReturnList = lendItemReturnService.selectLendItemReturnList(lendReturn.getId());
        for (LendItemReturn item : lendItemReturnList) {
            // 更新回款状态
            item.setStatus(1);
            item.setRealReturnTime(LocalDateTime.now());
            lendItemReturnMapper.updateById(item);

            // 更新出借信息
            LendItem lendItem = lendItemMapper.selectById(item.getLendItemId());
            lendItem.setRealAmount(lendItem.getRealAmount().add(item.getInterest()));// 动态的实际收益
            lendItemMapper.updateById(lendItem);

            // 投资账号转入金额
            String investBindCode = userBindService.getBindCodeByUserId(item.getInvestUserId());
            userAccountMapper.updateAccount(investBindCode, item.getTotal(), new BigDecimal(0));
            // 回款流水
            TransFlowBO investTransFlowBO = new TransFlowBO(
                    LendNoUtils.getReturnItemNo(),
                    investBindCode,
                    item.getTotal(),
                    TransTypeEnum.INVEST_BACK,
                    "还款到账，项目编号：" + lend.getLendNo() + "，项目名称：" + lend.getTitle());
            transFlowService.saveTransFlow(investTransFlowBO);
        }
    }
}
