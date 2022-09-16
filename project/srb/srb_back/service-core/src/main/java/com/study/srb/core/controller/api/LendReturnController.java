package com.study.srb.core.controller.api;


import com.study.srb.base.util.JwtUtils;
import com.study.srb.core.pojo.entity.LendReturn;
import com.study.srb.core.service.LendReturnService;
import com.study.srb.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "还款计划")
@RestController
@RequestMapping("/api/core/lendReturn")
@Slf4j
public class LendReturnController {

    @Resource
    private LendReturnService lendReturnService;

    @ApiOperation("获取列表")
    @GetMapping("/list/{lendId}")
    public R list(
            @ApiParam(value = "标的id", required = true)
            @PathVariable Long lendId) {
        List<LendReturn> list = lendReturnService.selectByLendId(lendId);
        return R.ok().data("list", list);
    }

    @ApiOperation("用户还款")
    @PostMapping("/auth/commitReturn/{lendReturnId}")
    public R commitReturn(
            @ApiParam(value = "还款计划id", required = true)
            @PathVariable Long lendReturnId, HttpServletRequest request) {

        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        String formStr = lendReturnService.commitReturn(lendReturnId, userId);
        return R.ok().data("formStr", formStr);
    }
}

