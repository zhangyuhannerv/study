package com.study.springcloud.service.impl;

import com.study.springcloud.dao.IStorageDao;
import com.study.springcloud.service.IStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StorageServiceImpl implements IStorageService {
    @Autowired
    IStorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        storageDao.decrease(productId, count);
    }
}
