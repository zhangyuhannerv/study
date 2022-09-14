package com.study.srb.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.core.enums.LendStatusEnum;
import com.study.srb.core.enums.ReturnMethodEnum;
import com.study.srb.core.enums.TransTypeEnum;
import com.study.srb.core.hfb.HfbConst;
import com.study.srb.core.hfb.RequestHelper;
import com.study.srb.core.mapper.BorrowerMapper;
import com.study.srb.core.mapper.LendMapper;
import com.study.srb.core.mapper.UserAccountMapper;
import com.study.srb.core.mapper.UserInfoMapper;
import com.study.srb.core.pojo.bo.TransFlowBO;
import com.study.srb.core.pojo.entity.*;
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
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private LendReturnService lendReturnService;

    @Resource
    private LendItemReturnService lendItemReturnService;

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
        // 获取借款人的bindCode
        BigDecimal voteAmt = new BigDecimal(result.getString("voteAmt"));
        Long userId = lend.getUserId();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        String bindCode = userInfo.getBindCode();
        userAccountMapper.updateAccount(bindCode, voteAmt, new BigDecimal(0));

//        （3）增加借款交易流水
        TransFlowBO transFlowBO = new TransFlowBO(
                result.getString("agentBillNo"),
                bindCode,
                voteAmt,
                TransTypeEnum.BORROW_BACK,
                "项目放款，编号：" + lend.getLendNo() + "，项目名称：" + lend.getTitle()
        );
        transFlowService.saveTransFlow(transFlowBO);

//        （4）解冻并扣除投资人资金
        // 获取标的下投资列表
        List<LendItem> lendItems = lendItemService.selectByLendId(lend.getId(), 1);
        lendItems.stream().forEach(item -> {
            // 投资人id
            Long investUserId = item.getInvestUserId();
            // 获取投资人用户
            UserInfo investUserInfo = userInfoMapper.selectById(investUserId);
            // 获取投资人的绑定编号
            String investBindCode = investUserInfo.getBindCode();
            // 扣除金额
            BigDecimal investAmount = item.getInvestAmount();
            userAccountMapper.updateAccount(investBindCode,
                    new BigDecimal(0),
                    investAmount.negate());
            // (5)增加投资人交易流水
            TransFlowBO investTransFlowBO = new TransFlowBO(
                    LendNoUtils.getTransNo(),
                    investBindCode,
                    investAmount,
                    TransTypeEnum.INVEST_UNLOCK,
                    "项目放款，冻结资金转出，项目编号：" + lend.getLendNo() + "，项目名称：" + lend.getTitle()
            );
            transFlowService.saveTransFlow(investTransFlowBO);
        });

//        （6）生成借款人还款计划和出借人回款计划
        this.repaymentPlan(lend);
    }

    /**
     * 还款计划
     *
     * @param lend
     */
    private void repaymentPlan(Lend lend) {
        //还款计划列表
        List<LendReturn> lendReturnList = new ArrayList<>();

        //按还款时间生成还款计划
        int len = lend.getPeriod().intValue();
        for (int i = 1; i <= len; i++) {
            // 创建还款计划对象
            LendReturn lendReturn = new LendReturn();
            // 填充基本属性
            lendReturn.setReturnNo(LendNoUtils.getReturnNo());
            lendReturn.setLendId(lend.getId());
            lendReturn.setBorrowInfoId(lend.getBorrowInfoId());
            lendReturn.setUserId(lend.getUserId());
            lendReturn.setAmount(lend.getAmount());
            lendReturn.setBaseAmount(lend.getInvestAmount());
            lendReturn.setLendYearRate(lend.getLendYearRate());
            lendReturn.setCurrentPeriod(i);//当前期数
            lendReturn.setReturnMethod(lend.getReturnMethod());

            lendReturn.setFee(new BigDecimal(0));
            lendReturn.setReturnDate(lend.getLendStartDate().plusMonths(i)); //第二个月开始还款
            lendReturn.setOverdue(false);

            // 判断是否是最后一期
            if (i == len) {
                // 最后一期
                lendReturn.setLast(true);
            } else {
                // 不是最后一期
                lendReturn.setLast(false);
            }

            // 设置还款状态
            lendReturn.setStatus(0);

            // 将还款计划加入到还款计划表
            lendReturnList.add(lendReturn);
        }
        // 批量保存还款计划
        lendReturnService.saveBatch(lendReturnList);

        // 生成期数和还款记录的id对应的键值对集合
        Map<Integer, Long> lendReturnMap = lendReturnList.stream().collect(
                Collectors.toMap(LendReturn::getCurrentPeriod, LendReturn::getId));

        // 创建所有投资的回款记录列表
        ArrayList<LendItemReturn> lendItemReturnAllList = new ArrayList<>();

        // 获取当前标的下所有已经支付的投资
        List<LendItem> lendItems = lendItemService.selectByLendId(lend.getId(), 1);
        for (LendItem lendItem : lendItems) {
            // 根据投资记录id调用回款计划生成的方法，得到当前这笔投资的回款计划列表
            List<LendItemReturn> lendItemReturnList = this.returnInvest(lendItem.getId(), lendReturnMap, lend);
            lendItemReturnAllList.addAll(lendItemReturnList);
        }

        // 遍历还款记录列表
        for (LendReturn lendReturn : lendReturnList) {
            // 通过filter，map，reduce将相关期数的回款数据过滤出来
            // 将当前期数的所有投资人的数据相加，就是当前期数所有投资人的回款数据（本金，利息，总金额）
            BigDecimal sumPrincipal = lendItemReturnAllList
                    .stream()
                    .filter(item -> Objects.equals(item.getLendReturnId(), lendReturn.getId()))
                    .map(LendItemReturn::getPrincipal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal sumInterest = lendItemReturnAllList
                    .stream()
                    .filter(item -> Objects.equals(item.getLendReturnId(), lendReturn.getId()))
                    .map(LendItemReturn::getInterest)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumTotal = lendItemReturnAllList
                    .stream()
                    .filter(item -> Objects.equals(item.getLendReturnId(), lendReturn.getId()))
                    .map(LendItemReturn::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            // 将计算出的数据填充入还款计划记录，设置本金，利息，总金额
            lendReturn.setPrincipal(sumPrincipal);
            lendReturn.setInterest(sumInterest);
            lendReturn.setTotal(sumTotal);
        }

        // 批量更新还款计划表
        lendReturnService.updateBatchById(lendReturnList);
    }

    /**
     * 回款计划(针对某一笔投资的回款计划）
     *
     * @param lendItemId
     * @param lendReturnMap 还款期数与还款计划id对应map
     * @param lend
     * @return
     */
    public List<LendItemReturn> returnInvest(Long lendItemId, Map<Integer, Long> lendReturnMap, Lend lend) {
        // 获取当前投资记录信息
        LendItem lendItem = lendItemService.getById(lendItemId);
        // 调用工具类计算还款本金和利息，存储为集合
        BigDecimal amount = lendItem.getInvestAmount();// 投资金额
        BigDecimal yearRate = lendItem.getLendYearRate();// 年化利率
        Integer totalMonth = lend.getPeriod();// 期数

        Map<Integer, BigDecimal> mapInterest = null;  //还款期数 -> 利息
        Map<Integer, BigDecimal> mapPrincipal = null; //还款期数 -> 本金

        //根据还款方式计算本金和利息
        if (lend.getReturnMethod().intValue() == ReturnMethodEnum.ONE.getMethod()) {
            //利息
            mapInterest = Amount1Helper.getPerMonthInterest(amount, yearRate, totalMonth);
            //本金
            mapPrincipal = Amount1Helper.getPerMonthPrincipal(amount, yearRate, totalMonth);
        } else if (lend.getReturnMethod().intValue() == ReturnMethodEnum.TWO.getMethod()) {
            mapInterest = Amount2Helper.getPerMonthInterest(amount, yearRate, totalMonth);
            mapPrincipal = Amount2Helper.getPerMonthPrincipal(amount, yearRate, totalMonth);
        } else if (lend.getReturnMethod().intValue() == ReturnMethodEnum.THREE.getMethod()) {
            mapInterest = Amount3Helper.getPerMonthInterest(amount, yearRate, totalMonth);
            mapPrincipal = Amount3Helper.getPerMonthPrincipal(amount, yearRate, totalMonth);
        } else {
            mapInterest = Amount4Helper.getPerMonthInterest(amount, yearRate, totalMonth);
            mapPrincipal = Amount4Helper.getPerMonthPrincipal(amount, yearRate, totalMonth);
        }

        // 创建回款计划列表
        List<LendItemReturn> lendItemReturnList = new ArrayList<>();

        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            Integer currentPeriod = entry.getKey();// 当前期数
            Long lendReturnId = lendReturnMap.get(currentPeriod);// 还款计划的id

            // 创建回款计划
            LendItemReturn lendItemReturn = new LendItemReturn();
            // 设置回款记录的基本属性
            lendItemReturn.setLendReturnId(lendReturnId);// 将还款记录关联到回款记录
            lendItemReturn.setLendItemId(lendItemId);
            lendItemReturn.setInvestUserId(lendItem.getInvestUserId());
            lendItemReturn.setLendId(lendItem.getLendId());
            lendItemReturn.setInvestAmount(lendItem.getInvestAmount());
            lendItemReturn.setLendYearRate(lend.getLendYearRate());
            lendItemReturn.setCurrentPeriod(currentPeriod);
            lendItemReturn.setReturnMethod(lend.getReturnMethod());

            // 计算回款本金，利息和总额（注意最后一个月的计算）
            if (currentPeriod.intValue() == lend.getPeriod()) {
                // 最后一期

                // 本金
                BigDecimal sumPrincipal = lendItemReturnList
                        .stream()
                        .map(LendItemReturn::getPrincipal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal lastPrincipal = lendItem.getInvestAmount().subtract(sumPrincipal);
                lendItemReturn.setPrincipal(lastPrincipal);

                // 利息
                BigDecimal sumInterest = lendItemReturnList
                        .stream()
                        .map(LendItemReturn::getInterest)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal lastInterest = lendItem.getExpectAmount().subtract(sumInterest);
                lendItemReturn.setInterest(lastInterest);

            } else {
                // 非最后一期
                lendItemReturn.setPrincipal(mapPrincipal.get(currentPeriod));
                lendItemReturn.setInterest(mapInterest.get(currentPeriod));
            }

            // 设置回款总金额
            lendItemReturn.setTotal(lendItemReturn.getPrincipal().add(lendItemReturn.getInterest()));

            // 设置回款状态和是否逾期等其他属性
            lendItemReturn.setFee(new BigDecimal("0"));
            lendItemReturn.setReturnDate(lend.getLendStartDate().plusMonths(currentPeriod));
            //是否逾期，默认未逾期
            lendItemReturn.setOverdue(false);
            lendItemReturn.setStatus(0);

            // 将回款记录放入回款列表
            lendItemReturnList.add(lendItemReturn);
        }

        // 批量保存
        lendItemReturnService.saveBatch(lendItemReturnList);

        return lendItemReturnList;
    }
}
