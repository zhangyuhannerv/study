package com.study.gulimall.search.service;

import com.study.gulimall.search.vo.SearchParam;

public interface MallSearchService {
    /**
     * @param searchParam 检索的所有参数
     * @return 返回检索的结果
     */
    Object search(SearchParam searchParam);
}
