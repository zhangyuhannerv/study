package com.study.srb.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.core.enums.LendStatusEnum;
import com.study.srb.core.enums.ReturnMethodEnum;
import com.study.srb.core.hfb.HfbConst;
import com.study.srb.core.hfb.RequestHelper;
import com.study.srb.core.mapper.BorrowerMapper;
import com.study.srb.core.mapper.LendMapper;
import com.study.srb.core.mapper.UserAccountMapper;
import com.study.srb.core.mapper.UserInfoMapper;
import com.study.srb.core.pojo.entity.BorrowInfo;
import com.study.srb.core.pojo.entity.Borrower;
import com.study.srb.core.pojo.entity.Lend;
import com.study.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.study.srb.core.pojo.vo.BorrowerDetailVO;
import com.study.srb.core.service.*;
import com.study.srb.core.util.*;
import com.study.srb.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 标的准备表 服务实现类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Service
@Slf4j
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService {
    @Resource
    private DictService dictService;

    @Resource
    private BorrowerService borrowerService;

    @Resource
    private BorrowerMapper borrowerMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private LendItemService lendItemService;

    @Resource
    private TransFlowService transFlowService;

    @Override
    public void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo) {
        Lend lend = new Lend();
        lend.setUserId(borrowInfo.getUserId());
        lend.setBorrowInfoId(borrowInfo.getId());
        lend.setLendNo(LendNoUtils.getLendNo());
        lend.setTitle(borrowInfoApprovalVO.getTitle());
        lend.setAmount(borrowInfo.getAmount());
        lend.setPeriod(borrowInfo.getPeriod());
        lend.setLendYearRate(borrowInfoApprovalVO.getLendYearRate().divide(new BigDecimal(100)));
        lend.setServiceRate(borrowInfoApprovalVO.getServiceRate().divide(new BigDecimal(100)));
        lend.setReturnMethod(borrowInfo.getReturnMethod());
        lend.setLowestAmount(new BigDecimal(100));
        lend.setInvestAmount(new BigDecimal(0));// 最低投资金额
        lend.setInvestNum(0);// 已投人数
        lend.setPublishDate(LocalDateTime.now());// 标的的发布时间

        // 启息日
        String lendStartDate = borrowInfoApprovalVO.getLendStartDate();
        lend.setLendStartDate(LocalDate.parse(lendStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        // 结束日期
        lend.setLendEndDate(lend.getLendStartDate().plusMonths(borrowInfo.getPeriod()));

        // 标的描述
        lend.setLendInfo(borrowInfoApprovalVO.getLendInfo());

        // 平台预期收益 = 标的金额 *（ 服务费率 / 12 * 期数）
        BigDecimal monthRate = lend.getServiceRate().divide(new BigDecimal(12), 8, RoundingMode.DOWN);
        BigDecimal expectAmount = lend.getAmount().multiply(monthRate.multiply(new BigDecimal(lend.getPeriod())));
        lend.setExpectAmount(expectAmount);

        // 实际收益
        lend.setRealAmount(new BigDecimal(0));

        // 标的状态(募资中)
        lend.setStatus(LendStatusEnum.INVEST_RUN.getStatus());

        // 审核时间
        lend.setCheckTime(LocalDateTime.now());

        // 审核人(这里随便给个值就行，不细写了）
        lend.setCheckAdminId(1L);

        // 存入数据库
        baseMapper.insert(lend);
    }

    @Override
    public List<Lend> selectList() {
        List<Lend> lendList = baseMapper.selectList(null);
        for (Lend lend : lendList) {
            String returnMethod = dictService.getNameByParentDictCodeAndValue("returnMethod", lend.getReturnMethod());
            String status = LendStatusEnum.getMsgByStatus(lend.getStatus());
            lend.getParam().put("returnMethod", returnMethod);
            lend.getParam().put("status", status);
        }
        return lendList;
    }

    @Override
    public Map<String, Object> getLendDetail(Long id) {
        // 查询标的 lend
        Lend lend = baseMapper.selectById(id);
        String returnMethod = dictService.getNameByParentDictCodeAndValue("returnMethod", lend.getReturnMethod());
        String status = LendStatusEnum.getMsgByStatus(lend.getStatus());
        lend.getParam().put("returnMethod", returnMethod);
        lend.getParam().put("status", status);

        // 查询借款人对象：Borrower(BorrowDetailVO)
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.eq("user_id", lend.getUserId());
        Borrower borrower = borrowerMapper.selectOne(borrowerQueryWrapper);

        BorrowerDetailVO borrowerDetailVO = borrowerService.getBorrowerDetailVOById(borrower.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("lend", lend);
        result.put("borrower", borrowerDetailVO);
        return result;
    }

    @Override
    public BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, Integer totalmonth, Integer returnMethod) {
        BigDecimal interestCount;
        if (Objects.equals(returnMethod, ReturnMethodEnum.ONE.getMethod())) {
            interestCount = Amount1Helper.getInterestCount(invest, yearRate, totalmonth);
        } else if (Objects.equals(returnMethod, ReturnMethodEnum.TWO.getMethod())) {
            interestCount = Amount2Helper.getInterestCount(invest, yearRate, totalmonth);
        } else if (Objects.equals(returnMethod, ReturnMethodEnum.THREE.getMethod())) {
            interestCount = Amount3Helper.getInterestCount(invest, yearRate, totalmonth);
        } else {
            interestCount = Amount4Helper.getInterestCount(invest, yearRate, totalmonth);
        }

        return interestCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void makeLoan(Long id) {
        // 获取标的信息
        Lend lend = baseMapper.selectById(id);

        // 调用汇付宝接口
        // 拼接参数
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", HfbConst.AGENT_ID);
        map.put("agentProjectCode", lend.getLendNo());
        map.put("agentBillNo", LendNoUtils.getLoanNo());

        // 月年化
        BigDecimal monthRate = lend.getServiceRate().divide(new BigDecimal(12), 8, RoundingMode.DOWN);
        // 平台服务费 = 已投金额 * 月年化 * 投资时长
        BigDecimal realAmount = lend.getInvestAmount().multiply(monthRate).multiply(new BigDecimal(lend.getPeriod()));
        map.put("mchFee", realAmount);

        map.put("timestamp", RequestHelper.getTimestamp());
        map.put("sign", RequestHelper.getSign(map));


        // 提交远程请求
        JSONObject result = RequestHelper.sendRequest(map, HfbConst.MAKE_LOAD_URL);
        log.info("放款结果:" + result.toJSONString());

        // 放款失败的处理
        if (!"0000".equals(result.getString("resultCode"))) {
            throw new BusinessException(result.getString("resultMsg"));
        }

        // 放款成功

//        （1）标的状态和标的平台收益：更新标的相关信息
        lend.setRealAmount(realAmount);// 平台收益
        lend.setStatus(LendStatusEnum.PAY_RUN.getStatus());// 更新标的的状态为还款中
        lend.setPaymentTime(LocalDateTime.now());// 放款时间
        baseMapper.updateById(lend);

//        （2）给借款账号转入金额
        userAccountMapper.updateAccount();
//        （3）增加借款交易流水
//        （4）解冻并扣除投资人资金
//        （5）增加投资人交易流水
//        （6）生成借款人还款计划和出借人回款计划
    }
}
