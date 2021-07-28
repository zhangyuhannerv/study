package com.zh.ExcelDemo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;

/**
 * @ClassName com.zh.ExcelDemo
 * @Description poi操作excel基础
 * @Author Zhangyuhan
 * @Date 2021/7/26
 * @Version 1.0
 */
public class ExcelDemo {
    public static void main(String[] args) {
        // 创建一个空的excel文件(工作簿)
        Workbook wb = new HSSFWorkbook();

        // 创建一个工作区
        Sheet sheeet = wb.createSheet("员工数据");
        // 设置列宽(内部算法，30*256可以接近实际的30宽)
        sheeet.setColumnWidth(2, 30 * 256);

        // 创建行
        // 参数是行号(从0开始)
        Row row = sheeet.createRow(1);
        // 设置行高
        row.setHeightInPoints(30f);

        // 创建单元格（2-c)
        // 列号也是从0开始
        Cell cell = row.createCell(2);

        // 给单元格设置样式
        // 先定义样式对象
        CellStyle cellStyle = wb.createCellStyle();
        // 给cellStyle一些样式
        // 例如：自定义字体
        Font font = wb.createFont();
        font.setBold(true);// 字体加粗
        font.setFontHeightInPoints((short) 20);// 字体大小
        font.setFontName("宋体");// 字体类型
        cellStyle.setFont(font);

        cell.setCellStyle(cellStyle);

        // 给单元格去设置内容
        cell.setCellValue("张雨晗22");

        // 把内存中的excel文件保存到本地的磁盘上
        // 先创建文件输出流
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(new File("C:/Users/13551/Desktop/demo.xls"));
            wb.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 回收内存中的对象
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
