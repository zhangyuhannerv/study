package com.study.srb.core.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.srb.base.util.JwtUtils;
import com.study.srb.core.pojo.entity.UserInfo;
import com.study.srb.core.pojo.query.UserInfoQuery;
import com.study.srb.core.pojo.vo.LoginVo;
import com.study.srb.core.pojo.vo.RegisterVO;
import com.study.srb.core.pojo.vo.UserInfoVO;
import com.study.srb.core.service.UserInfoService;
import com.study.srb.exception.Assert;
import com.study.srb.result.R;
import com.study.srb.result.ResponseEnum;
import com.study.srb.util.RegexValidateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Api(tags = "会员管理")
@RestController
@RequestMapping("/admin/core/userInfo")
@Slf4j
@CrossOrigin
public class AdminUserInfoController {
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("获取会员分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(
            @ApiParam(value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(value = "查询对象", required = false)
                    UserInfoQuery userInfoQuery
    ) {
        Page<UserInfo> userInfoPage = new Page<>(page, limit);
        IPage<UserInfo> pageModel = userInfoService.listPage(userInfoPage, userInfoQuery);
        return R.ok().data("pageModel", pageModel);
    }
}

