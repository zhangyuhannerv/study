package com.study.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.study.easyexcel.dto.ExcelStudentDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelStudentDTOListener extends AnalysisEventListener<ExcelStudentDTO> {
    @Override
    public void invoke(ExcelStudentDTO data, AnalysisContext analysisContext) {
        log.info("解析到一行数据:{}", data);
        // 调用dao层的方法。保存
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成");
    }
}
