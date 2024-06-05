package com.study.gulimall.search.service.impl;

import com.study.gulimall.search.config.GulimallElasticsearchConfig;
import com.study.gulimall.search.constant.EsConstant;
import com.study.gulimall.search.service.MallSearchService;
import com.study.gulimall.search.vo.SearchParam;
import com.study.gulimall.search.vo.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
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
            result = buildSearchResult(response);

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
    private SearchResult buildSearchResult(SearchResponse response) {
        return null;
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
            String[] s = param.getSkuPrice().split("_");
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

        // 聚合分析


        return new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, sourceBuilder);
    }
}
