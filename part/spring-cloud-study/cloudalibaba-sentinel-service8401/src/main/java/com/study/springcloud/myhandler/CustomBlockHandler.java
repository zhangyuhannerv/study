package com.study.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.study.springcloud.entities.CommonResult;

public class CustomBlockHandler {
    public static CommonResult handlerException(BlockException exception) {
        return new CommonResult(444, "按客户自定义,global,handlerException1");
    }

    public static CommonResult handlerException2(BlockException exception) {
        return new CommonResult(444, "按客户自定义,global,handlerException2");
    }
}
