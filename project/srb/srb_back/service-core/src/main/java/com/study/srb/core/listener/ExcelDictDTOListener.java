package com.study.srb.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.study.srb.core.mapper.DictMapper;
import com.study.srb.core.pojo.dto.ExcelDictDto;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelDictDTOListener extends AnalysisEventListener<ExcelDictDto> {
    private DictMapper dictMapper;
    // 每隔5条记录。存储一次数据（实际开发生产中一般1000~3000）
    private static final int BATCH_COUNT = 5;
    // 数据列表
    private List<ExcelDictDto> list = new ArrayList<>();

    public ExcelDictDTOListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(ExcelDictDto excelDictDto, AnalysisContext analysisContext) {
        log.info("解析到一条记录：{}", excelDictDto);
        // 将数据存入数据列表
        list.add(excelDictDto);
        if (list.size() >= BATCH_COUNT) {
            saveData();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 当最后剩余的数据记录数不足BATCH_COUNT时，我们最终一次性存储剩余数据
        if (!list.isEmpty()) {
            saveData();
        }

        log.info("所有数据解析完成！！");
    }

    private void saveData() {
        log.info("{}条数据被存储到数据库......", list.size());
        // 调用mapper层的save方法：save list 对象
        dictMapper.insertBatch(list);
        log.info("{}条数据存储到数据库成功", list.size());
        list.clear();
    }
}
