package com.study.gulimall.member.feign;

import com.study.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 声明式的远程调用接口
 */
@FeignClient("gulimall-coupon")// 调用该远程服务
public interface CouponFeignService {
    // 调用远程服务的某一请求
    @RequestMapping("/coupon/coupon/member/list")
    R memberCoupons();
}
