package com.study.springboot2study.orm.mybatis.mapper;

import com.study.springboot2study.bean.TestUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestUserMapper {
    TestUser getById(int id);
}
