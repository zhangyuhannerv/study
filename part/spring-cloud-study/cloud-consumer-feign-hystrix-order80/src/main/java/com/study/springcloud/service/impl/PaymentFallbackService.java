package com.study.springcloud.service.impl;

import com.study.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_ok(Integer id) {
        return "---PaymentFallbackService fallback paymentInfo_ok 哭泣";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "---PaymentFallbackService fallback paymentInfo_TimeOut 哭泣";
    }
}
