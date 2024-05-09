package com.study.gulimall.search.service;

import com.study.common.to.es.SkuEsModule;

import java.io.IOException;
import java.util.List;

public interface ElasticSaveService {
    Boolean productStatusUp(List<SkuEsModule> skuEsModules) throws IOException;
}
