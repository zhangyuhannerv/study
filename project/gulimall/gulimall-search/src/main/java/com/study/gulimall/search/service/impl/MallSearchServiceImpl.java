package com.study.gulimall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.study.common.to.es.SkuEsModule;
import com.study.gulimall.search.config.GulimallElasticsearchConfig;
import com.study.gulimall.search.constant.EsConstant;
import com.study.gulimall.search.service.MallSearchService;
import com.study.gulimall.search.vo.SearchParam;
import com.study.gulimall.search.vo.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MallSearchServiceImpl implements MallSearchService {
    @Autowired
    RestHighLevelClient elasticSearchClient;

    @Override
    public SearchResult search(SearchParam searchParam) {
        // 动态构建出查询所需要的dsl语句
        SearchResult result = null;
        // 准备检索请求
        SearchRequest searchRequest = buildSearchRequest(searchParam);
        try {
            // 执行检索请求
            SearchResponse response = elasticSearchClient.search(searchRequest, GulimallElasticsearchConfig.COMMON_OPTIONS);

            // 分析响应数据，封装成需要的格式
            result = buildSearchResult(response, searchParam);

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建结果数据
     *
     * @param response
     * @return
     */
    private SearchResult buildSearchResult(SearchResponse response, SearchParam searchParam) {
        SearchResult searchResult = new SearchResult();


        // 1.封装所有查询到的商品（从命中记录里封装）
        SearchHits hits = response.getHits();
        SearchHit[] hits_arr = hits.getHits();
        List<SkuEsModule> skuEsModules = new ArrayList<>();
        if (hits_arr != null) {
            for (SearchHit hit : hits_arr) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModule skuEsModule = JSON.parseObject(sourceAsString, SkuEsModule.class);

                // 封装高亮的信息
                if (StringUtils.isNotBlank(searchParam.getKeyword())) {
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    HighlightField skuTitle = highlightFields.get("skuTitle");
                    String skuTitleString = skuTitle.fragments()[0].string();
                    skuEsModule.setSkuTitle(skuTitleString);
                }

                skuEsModules.add(skuEsModule);
            }
        }

        searchResult.setProducts(skuEsModules);


        Aggregations aggregations = response.getAggregations();

        // 2.所有属性信息（从聚合里封装）
        List<SearchResult.AttrVo> attrVos = new ArrayList<>();
        ParsedNested attrAgg = aggregations.get("attr_agg");
        ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attr_id_agg");

        for (Terms.Bucket bucket : attrIdAgg.getBuckets()) {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            // 属性的id
            Long attrId = bucket.getKeyAsNumber().longValue();
            attrVo.setAttrId(attrId);
            // 属性的名称
            ParsedStringTerms attrNameAgg = bucket.getAggregations().get("attr_name_agg");
            String attrName = attrNameAgg.getBuckets().get(0).getKeyAsString();
            attrVo.setAttrName(attrName);
            // 属性的值
            ParsedStringTerms attrValueAgg = bucket.getAggregations().get("attr_value_agg");
            List<String> attrValues = new ArrayList<>();
            for (Terms.Bucket attrValueAggBucket : attrValueAgg.getBuckets()) {
                String attrValue = attrValueAggBucket.getKeyAsString();
                attrValues.add(attrValue);
            }
            attrVo.setAttrValue(attrValues);

            attrVos.add(attrVo);
        }

        searchResult.setAttrVos(attrVos);

        // 3.所有品牌信息（从聚合里封装）
        List<SearchResult.BrandVo> brandVos = new ArrayList<>();
        ParsedLongTerms brandAgg = aggregations.get("brand_agg");
        for (Terms.Bucket bucket : brandAgg.getBuckets()) {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
            // 得到品牌的id
            Long brandId = bucket.getKeyAsNumber().longValue();
            brandVo.setBrandId(brandId);
            // 得到品牌的图片
            ParsedStringTerms brandImgAgg = bucket.getAggregations().get("brand_img_agg");
            String brandImg = brandImgAgg.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandImg(brandImg);
            // 得到品牌的名称
            ParsedStringTerms brandNameAgg = bucket.getAggregations().get("brand_name_agg");
            String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandName(brandName);

            brandVos.add(brandVo);
        }
        searchResult.setBrands(brandVos);

        // 4.所有分类信息（从聚合里封装）
        ParsedLongTerms catalogAgg = aggregations.get("catalog_agg");
        List<? extends Terms.Bucket> buckets = catalogAgg.getBuckets();
        List<SearchResult.catalogVo> catalogVos = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            SearchResult.catalogVo catalogVo = new SearchResult.catalogVo();

            // 分类id
            String keyAsString = bucket.getKeyAsString();
            catalogVo.setCatalogId(Long.parseLong(keyAsString));

            // 得到分类名称
            ParsedStringTerms catalogNameAgg = bucket.getAggregations().get("catalog_name_agg");
            String catalogName = catalogNameAgg.getBuckets().get(0).getKeyAsString();
            catalogVo.setCatalogName(catalogName);

            catalogVos.add(catalogVo);
        }
        searchResult.setCatalogs(catalogVos);

        // 5.分页信息
        // 当前页
        searchResult.setPageNum(searchParam.getPageNum());
        // 总记录数
        Long total = hits.getTotalHits().value;
        searchResult.setTotal(total);
        // 总页数
        Long totalPages = total % EsConstant.PRODUCT_PAGE_SIZE == 0 ? total / EsConstant.PRODUCT_PAGE_SIZE :
                total / EsConstant.PRODUCT_PAGE_SIZE + 1;
        searchResult.setTotalPages(totalPages);


        return searchResult;
    }

    /**
     * 构建检索请求
     *
     * @param param
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParam param) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询，模糊匹配，过滤（属性，分类，品牌，价格区间，库存）
        // 构建boolQuery
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // must 模糊匹配
        if (StringUtils.isNotBlank(param.getKeyword())) {
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", param.getKeyword()));
        }
        // 第一个filter，按照三级分类id查询
        if (param.getCatalog3Id() != null) {
            boolQuery.filter(QueryBuilders.termQuery("catalogId", param.getCatalog3Id()));
        }
        // 第二个filter,多个品牌id
        if (param.getBrandId() != null && !param.getBrandId().isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("brandId", param.getBrandId()));
        }
        // 第三个filter，按照所有指定的属性进行查询
        // attrs=1_安卓:苹果&attrs=2_2.5寸:6寸
        if (param.getAttrs() != null && !param.getAttrs().isEmpty()) {
            for (String attr : param.getAttrs()) {
                // 每一个属性都要生成一个嵌入式的查询
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                String[] s = attr.split("_");
                String attrId = s[0];// 检索的属性id
                String[] attrValues = s[1].split(":");// 检索的多个属性值

                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));

                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }
        }

        // 第四个filter，按照是否有库存进行查询(0：无库存 1：有库存）
        boolQuery.filter(QueryBuilders.termQuery("hasStock", param.getHasStock() == 1));

        // 第五个filter，价格区间：1_500;_500,1_三种方式
        if (StringUtils.isNotBlank(param.getSkuPrice())) {
            String[] s = param.getSkuPrice().split("_", -1);
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
            if (s.length == 2) {
                if (StringUtils.isNotBlank(s[0])) {
                    rangeQuery.gte(s[0]);
//                    rangeQuery = rangeQuery.gte(s[0]);
                }

                if (StringUtils.isNotBlank(s[1])) {
                    rangeQuery.lte(s[1]);
//                    rangeQuery = rangeQuery.lte(s[1]);
                }
                boolQuery.filter(rangeQuery);
            }
        }

        // 把以前的所有条件都进行封装
        sourceBuilder.query(boolQuery);

        // 排序，分页，高亮
        // 排序
        if (StringUtils.isNotBlank(param.getSort())) {
            /**
             * sort=saleCount_asc/desc 销量
             * sort=skuPrice_asc/desc 价格
             * sort=hotScore_asc/desc 综合评分（热度分）
             */
            String[] s = param.getSort().split("_");
            sourceBuilder.sort(s[0], "asc".equalsIgnoreCase(s[1]) ? SortOrder.ASC : SortOrder.DESC);
        }

        // 分页
        sourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGE_SIZE);
        sourceBuilder.size(EsConstant.PRODUCT_PAGE_SIZE);
        // 高亮
        if (StringUtils.isNotBlank(param.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            sourceBuilder.highlighter(highlightBuilder);
        }


        // 聚合分析
        // 品牌聚合
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg");
        brandAgg.field("brandId").size(50);
        // 品牌聚合的子聚合
        brandAgg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brandAgg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        sourceBuilder.aggregation(brandAgg);

        // 分类聚合
        TermsAggregationBuilder catalogAgg = AggregationBuilders.terms("catalog_agg");
        catalogAgg.field("catalogId").size(20);
        // 分类聚合的子聚合
        catalogAgg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
        sourceBuilder.aggregation(catalogAgg);

        // 属性聚合（嵌入式聚合）
        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
        // 属性聚合子聚合：属性id
        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId").size(10);
        // 属性id子聚合的子聚合：属性名称
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        // 属性id子聚合的自聚合：所有可能的属性值
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));

        attr_agg.subAggregation(attrIdAgg);
        sourceBuilder.aggregation(attr_agg);

        return new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, sourceBuilder);
    }
}
