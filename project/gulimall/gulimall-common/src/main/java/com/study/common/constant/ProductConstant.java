package com.study.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ProductConstant {

    @Getter
    @AllArgsConstructor
    public enum AttrEnum {
        ATTR_TYPE_BASE(1, "基本属性"),
        ATTR_TYPE_SALE(0, "销售属性");

        private int code;
        private String msg;
    }

    @Getter
    @AllArgsConstructor
    public enum StatusEnum {
        NEW_SPU(0, "新建"),
        SPU_UP(1, "商品上架"),
        SPU_DOWN(2, "商品下架");

        private int code;
        private String msg;
    }

}
