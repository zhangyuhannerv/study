package com.study.springcloud.service.impl;

import com.study.springcloud.entities.CommonResult;
import com.study.springcloud.entities.Payment;
import com.study.springcloud.service.IPaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements IPaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444, "服务降级返回，----PaymentServiceImpl", null);
    }
}
