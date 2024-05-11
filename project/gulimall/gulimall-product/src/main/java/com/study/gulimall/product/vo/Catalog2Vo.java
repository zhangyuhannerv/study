package com.study.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Catalog2Vo {
    private String catalog1Id;// 1级父分类id
    private List<Catalog3Vo> catalog3List;// 3级子分类
    private String id;
    private String name;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Catalog3Vo {
        private String catalog2Id;// 2级父分类id
        private String id;
        private String name;
    }
}
