package com.study.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.study.easyexcel.dto.ExcelStudentDTO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelWriteTest {
    public static final String PATH = "D:\\javaTest\\";

    /**
     * 写07版excel
     */
    @Test
    public void simpleWriteTestXlsx() {
        // 写法1 JDK8+
        // since: 3.0.0-beta1
        String fileName = PATH + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, ExcelStudentDTO.class)
                .sheet("模板")
                .doWrite(data());
    }

    /**
     * 写03版excel
     */
    @Test
    public void simpleWriteTestXls() {
        // 写法1 JDK8+
        // since: 3.0.0-beta1
        String fileName = PATH + "simpleWrite" + System.currentTimeMillis() + ".xls";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, ExcelStudentDTO.class)
                .excelType(ExcelTypeEnum.XLS)
                .sheet("模板")
                .doWrite(data());
    }

    private List<ExcelStudentDTO> data() {
        List<ExcelStudentDTO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelStudentDTO data = new ExcelStudentDTO();
            data.setName("字符串" + i);
            data.setBirthday(new Date());
            data.setSalary(0.56);
            list.add(data);
        }
        return list;
    }

    private List<ExcelStudentDTO> largeData() {
        List<ExcelStudentDTO> list = new ArrayList<>();
        for (int i = 0; i < 60000; i++) {
            ExcelStudentDTO data = new ExcelStudentDTO();
            data.setName("字符串" + i);
            data.setBirthday(new Date());
            data.setSalary(0.56);
            list.add(data);
        }
        return list;
    }
}
