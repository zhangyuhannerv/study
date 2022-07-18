package com.study.springcloud.service.impl;

import com.study.springcloud.dao.IOrderDao;
import com.study.springcloud.domain.Order;
import com.study.springcloud.service.IAccountService;
import com.study.springcloud.service.IOrderService;
import com.study.springcloud.service.IStorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {
    @Autowired
    IOrderDao orderDao;
    @Autowired
    IStorageService storageService;
    @Autowired
    IAccountService accountService;

    @Override
    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("---->开始新建订单");
        orderDao.create(order);
        log.info("---->订单微服务开始调用库存，做扣减商品数量");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("---->订单微服务开始调用账户，做扣减余额");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("---->修改订单的状态从0到1。1表示已经完成");
        orderDao.update(order.getUserId(), 0);
        log.info("---->下订单结束");
    }
}
