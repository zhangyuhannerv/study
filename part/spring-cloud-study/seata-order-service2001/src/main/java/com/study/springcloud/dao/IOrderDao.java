package com.study.springcloud.dao;

import com.study.springcloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IOrderDao {
    // 1.新建订单
    void create(Order order);

    // 2.修改订单状态，0->1
    void update(@Param("userId") Long userId,
                @Param("status") Integer status);
}
