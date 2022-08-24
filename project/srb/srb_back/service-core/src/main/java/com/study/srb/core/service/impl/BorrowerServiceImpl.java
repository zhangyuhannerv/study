package com.study.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.study.srb.core.enums.BorrowerStatusEnum;
import com.study.srb.core.mapper.BorrowerAttachMapper;
import com.study.srb.core.mapper.UserInfoMapper;
import com.study.srb.core.pojo.entity.Borrower;
import com.study.srb.core.mapper.BorrowerMapper;
import com.study.srb.core.pojo.entity.BorrowerAttach;
import com.study.srb.core.pojo.entity.UserInfo;
import com.study.srb.core.pojo.vo.BorrowerVO;
import com.study.srb.core.service.BorrowerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Service
public class BorrowerServiceImpl extends ServiceImpl<BorrowerMapper, Borrower> implements BorrowerService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private BorrowerAttachMapper borrowerAttachMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId) {
        // 获取用户基本信息
        UserInfo userInfo = userInfoMapper.selectById(userId);

        // 保存借款人信息
        Borrower borrower = new Borrower();
        BeanUtils.copyProperties(borrowerVO, borrower);
        borrower.setUserId(userId);
        borrower.setName(userInfo.getName());
        borrower.setIdCard(userInfo.getIdCard());
        borrower.setMobile(userInfo.getMobile());
        borrower.setStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        baseMapper.insert(borrower);

        // 保存附件
        List<BorrowerAttach> borrowerAttachList = borrowerVO.getBorrowerAttachList();
        borrowerAttachList.forEach(borrowerAttach -> {
            borrowerAttach.setBorrowerId(borrower.getId());
            borrowerAttachMapper.insert(borrowerAttach);
        });

        // 更新userInfo中的借款人认证状态
        userInfo.setBorrowAuthStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public Integer getStatusByUserId(Long userId) {
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.select("status").eq("user_id", userId);
        List<Object> objects = baseMapper.selectObjs(borrowerQueryWrapper);
        if (objects.isEmpty()) {
            return BorrowerStatusEnum.NO_AUTH.getStatus();
        }

        return (Integer) objects.get(0);
    }

    @Override
    public IPage<Borrower> listPage(IPage<Borrower> pageParam, String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return baseMapper.selectPage(pageParam, null);
        }

        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper
                .like("name", keyword)
                .or()
                .like("id_card", keyword)
                .or()
                .like("mobile", keyword)
                .orderByDesc("id");
        return baseMapper.selectPage(pageParam, borrowerQueryWrapper);
    }
}
