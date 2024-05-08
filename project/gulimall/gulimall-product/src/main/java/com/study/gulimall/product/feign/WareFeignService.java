package com.study.gulimall.product.feign;

import com.study.common.to.SkuHasStockTo;
import com.study.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-ware")
public interface WareFeignService {
    @PostMapping("/ware/waresku/hasStock")
    R<List<SkuHasStockTo>> getSkusHasStock(@RequestBody List<Long> skuIds);
}
