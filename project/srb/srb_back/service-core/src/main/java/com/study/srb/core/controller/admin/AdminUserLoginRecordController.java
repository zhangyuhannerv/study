package com.study.srb.core.controller.admin;


import com.study.srb.core.pojo.entity.UserLoginRecord;
import com.study.srb.core.service.UserLoginRecordService;
import com.study.srb.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户登录记录表 前端控制器
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Api(tags = "会员登录日志接口")
@RestController
@RequestMapping("/admin/core/userLoginRecord")
@Slf4j
// 在gataway里配置了跨域，和@CrossRoigin冲突
// @CrossOrigin
public class AdminUserLoginRecordController {
    @Resource
    private UserLoginRecordService userLoginRecordService;

    @ApiOperation("获取会员登陆日志列表")
    @GetMapping("/listTop50/{userId}")
    public R listTop50(
            @ApiParam(value = "用户id", required = true)
            @PathVariable Long userId
    ) {
        List<UserLoginRecord> userLoginRecords = userLoginRecordService.listTop50(userId);
        return R.ok().data("list", userLoginRecords);
    }
}

