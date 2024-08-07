package com.study.srb.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "会员搜索对象")
public class UserInfoQuery {
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("1:出借人 2：借款人")
    private Integer userType;
}
