package com.study.gulimall.search.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.common.to.es.SkuEsModule;
import com.study.gulimall.search.config.GulimallElasticsearchConfig;
import com.study.gulimall.search.constant.EsConstant;
import com.study.gulimall.search.service.ElasticSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ElasticSaveServiceImpl implements ElasticSaveService {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public Boolean productStatusUp(List<SkuEsModule> skuEsModules) throws IOException {
        // 数据保存到es中

        // 1.预先给es建立索引，并建立好映射关系

        // 2.给es中保存数据
        // 批量存储
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModule skuEsModule : skuEsModules) {
            // 指定索引
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            // id
            indexRequest.id(skuEsModule.getSkuId().toString());
            // 数据
            String jsonString = new ObjectMapper().writeValueAsString(skuEsModule);
            indexRequest.source(jsonString, XContentType.JSON);

            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, GulimallElasticsearchConfig.COMMON_OPTIONS);
        // 如果有错误，还需处理
        List<String> collect = Arrays.stream(bulk.getItems())
                .filter(BulkItemResponse::isFailed)
                .map(BulkItemResponse::getId).collect(Collectors.toList());

        log.error("商品上架错误:{}", collect);

        return bulk.hasFailures();
    }
}
