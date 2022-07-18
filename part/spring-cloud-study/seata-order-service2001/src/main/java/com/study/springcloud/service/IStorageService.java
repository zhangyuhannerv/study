package com.study.springcloud.service;

import com.study.springcloud.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("seata-storage-service")
public interface IStorageService {
    @PostMapping("/storage/decrease")
    CommonResult decrease(@RequestParam("productId") Long id,
                          @RequestParam("count") Integer count);
}
