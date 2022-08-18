package com.study.srb.core.controller.api;

import com.alibaba.fastjson.JSON;
import com.study.srb.base.util.JwtUtils;
import com.study.srb.core.hfb.RequestHelper;
import com.study.srb.core.pojo.vo.UserBindVo;
import com.study.srb.core.service.UserBindService;
import com.study.srb.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api("会员账号绑定")
@RestController
@RequestMapping("/api/core/userBind")
@Slf4j
public class UserBindController {
    @Resource
    private UserBindService userBindService;

    @ApiOperation("账户绑定提交数据")
    @PostMapping("/auth/bind")
    public R bind(@RequestBody UserBindVo userBindVo, HttpServletRequest request) {
        // 从header中获取token，并对token进行校验，确保用户已经登陆。并从token中获取userId
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);

        // 根据userId做账户绑定,生成一个动态表单的字符串
        String formStr = userBindService.commitBindUser(userBindVo, userId);
        return R.ok().data("formStr", formStr);
    }

    @ApiOperation("账户绑定异步回调")
    @PostMapping("notify")
    public String notify(HttpServletRequest request) {
        // 汇付宝向尚荣宝发起回调请求时携带的参数
        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("账户绑定异步回调接收的参数如下：" + JSON.toJSONString(paramMap));

        // 校验签名
        if (!RequestHelper.isSignEquals(paramMap)) {
            log.error("用户账号绑定异步回调签名验证错误：" + JSON.toJSONString(paramMap));
            return "fail";
        }

        log.error("验签成功！开始账户绑定");
        userBindService.notify(paramMap);
        return "success";
    }
}
