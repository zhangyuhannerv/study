package com.study.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.study.easyexcel.dto.ExcelStudentDTO;
import com.study.easyexcel.listener.ExcelStudentDTOListener;
import org.junit.Test;

public class ExcelReadTest {
    public static final String PATH = "D:\\javaTest\\simpleWrite1659083569698.xlsx";
    public static final String PATH1 = "D:\\javaTest\\simpleWrite1659083836438.xls";

    @Test
    public void testSimpleReadXlsx() {
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        String fileName = PATH;
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        EasyExcel.read(fileName, ExcelStudentDTO.class, new ExcelStudentDTOListener()).sheet().doRead();
    }

    @Test
    public void testSimpleReadXls() {
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        String fileName = PATH1;
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        EasyExcel.read(fileName, ExcelStudentDTO.class, new ExcelStudentDTOListener())
                .excelType(ExcelTypeEnum.XLS).sheet().doRead();
    }
}
