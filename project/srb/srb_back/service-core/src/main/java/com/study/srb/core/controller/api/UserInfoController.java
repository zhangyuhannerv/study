package com.study.srb.core.controller.api;


import com.study.srb.core.pojo.dto.vo.RegisterVO;
import com.study.srb.core.service.UserInfoService;
import com.study.srb.exception.Assert;
import com.study.srb.result.R;
import com.study.srb.result.ResponseEnum;
import com.study.srb.util.RegexValidateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Api(tags = "会员接口")
@RestController
@RequestMapping("/api/core/userInfo")
@Slf4j
@CrossOrigin
public class UserInfoController {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("会员注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVO registerVO) {
        String mobile = registerVO.getMobile();
        String password = registerVO.getPassword();
        String code = registerVO.getCode();

        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);
        Assert.notEmpty(code, ResponseEnum.CODE_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);


        // 校验验证码是否正确
        String codeGen = (String) redisTemplate.opsForValue().get("srb:sms:code:" + mobile);
        Assert.equals(code, codeGen, ResponseEnum.CODE_ERROR);

        // 注册
        userInfoService.register(registerVO);

        return R.ok().message("注册成功");
    }
}

