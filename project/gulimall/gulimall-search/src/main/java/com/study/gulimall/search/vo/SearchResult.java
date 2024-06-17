package com.study.gulimall.search.vo;

import com.study.common.to.es.SkuEsModule;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    // 查询到的所有商品信息
    private List<SkuEsModule> products;
    // 以下是分页信息
    private Integer pageNum;// 当前页面
    private Long total;// 总记录数
    private Long totalPages;// 总页码
    private List<Long> pageNavs;// 数组，存储所有的页码

    // 当前查询到的结果所有涉及到的品牌
    private List<BrandVo> brands;
    // 当前查询到的结果所有涉及到的分心
    private List<catalogVo> catalogs;
    // 当前查询到的结果所有涉及到的属性
    private List<AttrVo> attrVos;

    @Data
    public static class BrandVo {
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    @Data
    public static class AttrVo {
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }

    @Data
    public static class catalogVo {
        private Long catalogId;
        private String catalogName;
    }


}
