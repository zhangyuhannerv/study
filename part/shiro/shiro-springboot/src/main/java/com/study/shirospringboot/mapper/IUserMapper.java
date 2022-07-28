package com.study.shirospringboot.mapper;

import com.study.shirospringboot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IUserMapper {
    public User queryUserByName(String name);
}
