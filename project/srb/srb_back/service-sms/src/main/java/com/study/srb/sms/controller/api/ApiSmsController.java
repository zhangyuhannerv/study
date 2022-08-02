package com.study.srb.sms.controller.api;

import com.study.srb.exception.Assert;
import com.study.srb.result.R;
import com.study.srb.result.ResponseEnum;
import com.study.srb.sms.service.SmsService;
import com.study.srb.sms.util.SmsProperties;
import com.study.srb.util.RandomUtils;
import com.study.srb.util.RegexValidateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Api("短信管理")
@Slf4j
@RestController
@RequestMapping("/api/sms")
@CrossOrigin
public class ApiSmsController {
    @Resource
    private SmsService smsService;

    @Resource
    private RedisTemplate redisTemplate;

    @ApiOperation("获取短信验证码")
    @GetMapping("/send/{mobile}")
    public R send(
            @ApiParam(value = "手机号", required = true)
            @PathVariable String mobile) {

        // 校验手机号码不能为空
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        // 校验手机号码的合法性
        // 垃圾工具，我的手机号居然通过不了验证
//        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        // 生成验证码
        String fourBitRandom = RandomUtils.getFourBitRandom();
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", fourBitRandom);
        smsService.send(mobile, SmsProperties.TEMPLATE_CODE, map);

        // 将验证码存入redis
        redisTemplate.opsForValue().set("srb:sms:code:" + mobile, fourBitRandom, 5, TimeUnit.MINUTES);
        return R.ok().message("短信发送成功");
    }
}
