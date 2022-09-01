package com.study.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.srb.core.enums.LendStatusEnum;
import com.study.srb.core.mapper.BorrowerMapper;
import com.study.srb.core.mapper.LendMapper;
import com.study.srb.core.pojo.entity.BorrowInfo;
import com.study.srb.core.pojo.entity.Borrower;
import com.study.srb.core.pojo.entity.Lend;
import com.study.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.study.srb.core.pojo.vo.BorrowerDetailVO;
import com.study.srb.core.service.BorrowerService;
import com.study.srb.core.service.DictService;
import com.study.srb.core.service.LendService;
import com.study.srb.core.util.LendNoUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 服务实现类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Service
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService {
    @Resource
    private DictService dictService;

    @Resource
    private BorrowerService borrowerService;

    @Resource
    private BorrowerMapper borrowerMapper;

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
}
