package com.study.gulimall.member.dao;

import com.study.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author ZhangYuhan
 * @email zhangyuhannerv@gmail.com
 * @date 2023-12-11 16:29:13
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
