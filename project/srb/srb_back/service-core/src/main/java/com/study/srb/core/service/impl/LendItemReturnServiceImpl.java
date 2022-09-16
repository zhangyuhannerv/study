package com.study.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.core.mapper.LendItemMapper;
import com.study.srb.core.mapper.LendItemReturnMapper;
import com.study.srb.core.mapper.LendMapper;
import com.study.srb.core.mapper.LendReturnMapper;
import com.study.srb.core.pojo.entity.Lend;
import com.study.srb.core.pojo.entity.LendItem;
import com.study.srb.core.pojo.entity.LendItemReturn;
import com.study.srb.core.pojo.entity.LendReturn;
import com.study.srb.core.service.LendItemReturnService;
import com.study.srb.core.service.UserBindService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借回款记录表 服务实现类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Service
public class LendItemReturnServiceImpl extends ServiceImpl<LendItemReturnMapper, LendItemReturn> implements LendItemReturnService {
    @Resource
    private LendMapper lendMapper;

    @Resource
    private LendReturnMapper lendReturnMapper;

    @Resource
    private LendItemMapper lendItemMapper;

    @Resource
    private UserBindService userBindService;

    @Override
    public List<LendItemReturn> selectByLendId(Long lendId, Long userId) {
        QueryWrapper<LendItemReturn> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("lend_id", lendId)
                .eq("invest_user_id", userId)
                .orderByAsc("current_period");
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 通过还款记录的id找到对应的回款计划数据，组装data参数中需要的List<Map>
     *
     * @param lendReturnId
     * @return
     */
    @Override
    public List<Map<String, Object>> addReturnDetail(Long lendReturnId) {
        // 还款记录
        LendReturn lendReturn = lendReturnMapper.selectById(lendReturnId);
        // 获取标的
        Lend lend = lendMapper.selectById(lendReturn.getLendId());

        List<LendItemReturn> lendItemReturnList = selectLendItemReturnList(lendReturnId);
        List<Map<String, Object>> lendItemReturnDetailList = new ArrayList<>();
        for (LendItemReturn lendItemReturn : lendItemReturnList) {
            // 获取投资记录
            Long lendItemId = lendItemReturn.getLendItemId();
            LendItem lendItem = lendItemMapper.selectById(lendItemId);
            // 获取投资人id
            Long investUserId = lendItem.getInvestUserId();
            String bindCode = userBindService.getBindCodeByUserId(investUserId);

            Map<String, Object> map = new HashMap<>();
            //项目编号
            map.put("agentProjectCode", lend.getLendNo());// 项目编号
            //出借编号ø
            map.put("voteBillNo", lendItem.getLendItemNo());// 投资编号
            //收款人（出借人）
            map.put("toBindCode", bindCode);
            //还款金额
            map.put("transitAmt", lendItemReturn.getTotal());
            //还款本金
            map.put("baseAmt", lendItemReturn.getPrincipal());
            //还款利息
            map.put("benifitAmt", lendItemReturn.getInterest());
            //商户手续费
            map.put("feeAmt", new BigDecimal("0"));

            lendItemReturnDetailList.add(map);
        }
        return lendItemReturnDetailList;
    }

    @Override
    public List<LendItemReturn> selectLendItemReturnList(Long lendReturnId) {
        QueryWrapper<LendItemReturn> lendItemReturnQueryWrapper = new QueryWrapper<>();
        lendItemReturnQueryWrapper.eq("lend_return_id", lendReturnId);
        return baseMapper.selectList(lendItemReturnQueryWrapper);
    }
}
