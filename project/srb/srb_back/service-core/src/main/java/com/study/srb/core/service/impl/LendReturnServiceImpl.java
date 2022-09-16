package com.study.srb.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.core.hfb.FormHelper;
import com.study.srb.core.hfb.HfbConst;
import com.study.srb.core.hfb.RequestHelper;
import com.study.srb.core.mapper.LendMapper;
import com.study.srb.core.mapper.LendReturnMapper;
import com.study.srb.core.pojo.entity.Lend;
import com.study.srb.core.pojo.entity.LendReturn;
import com.study.srb.core.service.LendItemReturnService;
import com.study.srb.core.service.LendReturnService;
import com.study.srb.core.service.UserAccountService;
import com.study.srb.core.service.UserBindService;
import com.study.srb.exception.Assert;
import com.study.srb.result.ResponseEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
public class LendReturnServiceImpl extends ServiceImpl<LendReturnMapper, LendReturn> implements LendReturnService {

    @Resource
    private LendMapper lendMapper;

    @Resource
    private UserBindService userBindService;

    @Resource
    private UserAccountService userAccountService;

    @Resource
    private LendItemReturnService lendItemReturnService;

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
}
