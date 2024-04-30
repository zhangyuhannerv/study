package com.study.gulimall.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.gulimall.search.config.GulimallElasticsearchConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class SearchTest {
    @Autowired
    RestHighLevelClient client;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class User {
        private String userName;
        private String gender;
        private Integer age;

    }

    @Data
    @ToString
    public static class Account {
        private int account_number;
        private int balance;
        private String firstname;
        private String lastname;
        private int age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;
    }

    @Test
    public void contextLoads() {
        System.out.println(client);
    }

    /**
     * 测试存储数据到es(没有指定id，会自动生成一个id，并将数据保存）
     * 更新也可以
     */
    @Test
    public void indexData() throws IOException {
        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("1");
//        indexRequest.source("userName", "zhangsan", "gender", "男", "age", "18");
        User user = new User("张三", "男", 18);
        String jsonString = new ObjectMapper().writeValueAsString(user);
        indexRequest.source(jsonString, XContentType.JSON);

        // 执行操作
        IndexResponse index = client.index(indexRequest, GulimallElasticsearchConfig.COMMON_OPTIONS);

        System.out.println(index);

    }

    @Test
    public void searchData() throws IOException {
        // 1.创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        // 指定索引
        searchRequest.indices("bank");
        // 指定DSL
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 1.1构造检索条件
        // 查全部
//        sourceBuilder.query(QueryBuilders.matchAllQuery());
        // 查某个字段
        sourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));
//        sourceBuilder.from();
//        sourceBuilder.size();
        // 按照年龄的值分布进行聚合
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
        // 计算平均薪资
        AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");
        sourceBuilder.aggregation(ageAgg).aggregation(balanceAvg);
        System.out.println(sourceBuilder);
        searchRequest.source(sourceBuilder);
        // 2.执行检索
        SearchResponse searchResponse = client.search(searchRequest, GulimallElasticsearchConfig.COMMON_OPTIONS);

        // 3.分析结果
        System.out.println(searchResponse);

        // 3.1获取所有查到的数据
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
//            String index = searchHit.getIndex();
//            String type = searchHit.getType();
//            String id = searchHit.getId();
            String sourceAsString = searchHit.getSourceAsString();
            Account account = new ObjectMapper().readValue(sourceAsString, Account.class);
            System.out.println("account:" + account);
        }

        // 3.2获取这次检索聚合分析数据
        Aggregations aggregations = searchResponse.getAggregations();
        for (Aggregation aggregation : aggregations.asList()) {
            String name = aggregation.getName();
            System.out.println("当前聚合的名字：" + name);
        }

        Terms ageAgg1 = aggregations.get("ageAgg");
        for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println("年龄：" + keyAsString + ",人数：" + bucket.getDocCount());
        }

        Avg balanceAvg1 = aggregations.get("balanceAvg");
        System.out.println("平均薪资：" + balanceAvg1.getValue());
    }
}
