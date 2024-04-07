package com.study.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class WareConstant {
    @Getter
    @AllArgsConstructor
    public enum PurchaseStatusEnum {
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        RECEIVE(2, "已领取"),
        FINISH(3, "已完成"),
        HASERROR(4, "有异常");

        private int code;
        private String msg;
    }

    @Getter
    @AllArgsConstructor
    public enum PurchaseDetailStatusEnum {
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        BUYING(2, "正在采购"),
        FINISH(3, "已完成"),
        HASERROR(4, "采购失败");

        private int code;
        private String msg;
    }
}
