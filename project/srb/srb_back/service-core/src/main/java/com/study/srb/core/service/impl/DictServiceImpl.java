package com.study.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.study.srb.core.listener.ExcelDictDTOListener;
import com.study.srb.core.pojo.dto.ExcelDictDto;
import com.study.srb.core.pojo.entity.Dict;
import com.study.srb.core.mapper.DictMapper;
import com.study.srb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Resource
    private DictMapper dictMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDto.class, new ExcelDictDTOListener(dictMapper)).sheet().doRead();
        log.info("Excel导入成功");
    }
}
