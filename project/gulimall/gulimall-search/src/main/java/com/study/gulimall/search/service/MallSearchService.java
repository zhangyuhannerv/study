package com.study.gulimall.search.service;

import com.study.gulimall.search.vo.SearchParam;
import com.study.gulimall.search.vo.SearchResult;

public interface MallSearchService {
    /**
     * @param searchParam 检索的所有参数
     * @return 返回检索的结果
     */
    SearchResult search(SearchParam searchParam);
}
