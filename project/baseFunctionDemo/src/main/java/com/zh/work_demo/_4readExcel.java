package com.zh.work_demo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;


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

        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {// 遍历行数

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

        // 以上是获取数据部分
        // 之后是文件的数据处理部分

        // 掐头去尾取中间里程
        // 开头重复里程取最后一个
        // 结尾重复里程取第一个
        List<String> mileList = map2.get("里程");
        Integer subStart = -1;
        Integer subEnd = -1;
        if (mileList != null && mileList.size() > 1) {// 说明有里程这一列，并且数据量至少有两个
            if (mileList.get(0).equals(mileList.get(1))) {// 开头有里程重复数据(通过前两个里程一样得到)
                subStart = 1;
                for (int i = 2; i < mileList.size(); i++) {// 从第三个开始循环(如果有)
                    if (!mileList.get(i).equals(mileList.get(i - 1))) {// 当前里程不等于前一个里程
                        subStart = --i;// 保留前一个里程的索引
                        break;
                    }
                }
            }

            // 和上面同理，只不过是从后向前遍历
            if (mileList.get(mileList.size() - 1).equals(mileList.get(mileList.size() - 2))) {// 结尾有里程重复数据(通过最后两个里程一样得到)
                subEnd = mileList.size() - 2;
                for (int i = mileList.size() - 3; i >= 0; i--) {// 从倒数三个开始循环(如果有)
                    if (!mileList.get(i).equals(mileList.get(i + 1))) {
                        subEnd = ++i;
                        break;
                    }
                }
            }
        }
        for (Map.Entry<String, List<String>> entry : map2.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();

            // 头部有重复的里程，尾部没有
            if (subStart != -1 && subEnd == -1) {
                value = value.subList(subStart, value.size());
            } else if (subStart == -1 && subEnd != -1) {// 尾部有重复的里程，头部没有
                value = value.subList(0, subEnd + 1);
            } else if (subStart != -1 && subEnd != -1) {// 头尾都有重复的里程
                if (subEnd >= subStart) {
                    value = value.subList(subStart, subEnd + 1);
                } else {// 此时，整个文件的里程都是重复的，那么默认只取第一个
                    value = value.subList(0, 1);
                }
            }
            // 重新赋值
            map2.put(key, value);

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
