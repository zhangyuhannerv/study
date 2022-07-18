package com.study.srb_mybatis_plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.srb_mybatis_plus.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> selectAllByName(String name);
}
