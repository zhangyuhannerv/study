package com.study.srb.core.controller.api;

import com.study.srb.base.util.JwtUtils;
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
}
