package com.zh.work_demo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName _4readExcel
 * @Description 利用poi读取excel, 包括.xls和.xlsx
 * @Author Zhangyuhan
 * @Date 2021/10/11
 * @Version 1.0
 */
public class _4readExcel {
    public static Map<String, List<String>> getExcelData(String filePath) {
        Map<Integer, String> map1 = new LinkedHashMap<>();
        Map<String, List<String>> map2 = new LinkedHashMap<>();

        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        // 只读第一个工作区
        Integer sheetNumber = 0;

        wb = readExcel(filePath);

        // 空值,说明文件路径有问题或者文件格式不对
        if (wb == null) {
            return map2;
        }

        // 获取的第一个工作区
        sheet = wb.getSheetAt(sheetNumber);

        for (int i = 0; i < sheet.getLastRowNum(); i++) {// 遍历行数

            row = sheet.getRow(i);

            for (int j = 0; j < row.getLastCellNum(); j++) {// 遍历列(每一个单元格)
                cell = sheet.getRow(i).getCell(j);
                String cellValue = getCellValue(cell);
                if (i == 0) {
                    map1.put(j, cellValue);
                    map2.put(cellValue, new ArrayList<>());
                } else {
                    map2.get(map1.get(j)).add(cellValue);
                }
            }
        }

        return map2;
    }

    //判断文件格式
    private static Workbook readExcel(String filePath) {
        Workbook workbook = null;

        if (filePath == null) {
            return null;
        }

        // File file = new File(filePath);
        // if (!file.exists()) {
        //     return null;
        // }

        String fileType = filePath.substring(filePath.lastIndexOf("."));

        ClassPathResource classPathResource = new ClassPathResource(filePath);
        // 这里io流会自动关闭，无需加finally
        // try (InputStream is = new FileInputStream(file)) {// 读取绝对路径文件
        try (InputStream is = classPathResource.getInputStream()) {// 读取resources下文件方式1（适用于静态)
            // try (InputStream is = this.getClass().getResourceAsStream(filePath)) {// 读取相resources下文件方式2(不适用于静态)
            if (".xls".equals(fileType)) {
                workbook = new HSSFWorkbook(is);
            } else if (".xlsx".equals(fileType)) {
                workbook = new XSSFWorkbook(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
            workbook = null;
        }
        return workbook;
    }


    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "0";
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "0";
        }
    }
}
