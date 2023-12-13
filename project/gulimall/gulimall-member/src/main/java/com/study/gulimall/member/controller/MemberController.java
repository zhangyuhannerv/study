package com.study.gulimall.member.controller;

import com.study.common.utils.PageUtils;
import com.study.common.utils.R;
import com.study.gulimall.member.entity.MemberEntity;
import com.study.gulimall.member.feign.CouponFeignService;
import com.study.gulimall.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 会员
 *
 * @author ZhangYuhan
 * @email zhangyuhannerv@gmail.com
 * @date 2023-12-11 16:29:13
 */
@RestController
@RefreshScope
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponFeignService couponFeignService;
    @Value("${user.age}")
    private String userAge;

    /**
     * 测试feign
     *
     * @return
     */
    @RequestMapping("/coupons")
    public R coupons() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        R coupons = couponFeignService.memberCoupons();
        return R.ok().put("member", memberEntity).put("coupons", coupons.get("coupons"));
    }

    /**
     * 测试nacos注册中心
     */
    @RequestMapping("/testNacosConfig")
    public R testNacosConfig() {
        return R.ok().put("userName", userAge);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
