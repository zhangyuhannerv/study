package com.study.srb.core.service;

import com.study.srb.core.pojo.dto.ExcelDictDto;
import com.study.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author Zhangyuhan
 * @since 2022-07-25
 */
public interface DictService extends IService<Dict> {
    void importData(InputStream inputStream);

    List<ExcelDictDto> listDictData();
}
