package excel.demo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName ExcelWriteTest
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/11
 * @Version 1.0
 */
public class ExcelWriteTest {
    String path = "D:\\Work\\Study\\poiAndEasyExcel\\";

    @Test
    public void testWrite03() throws Exception {
        // 1.创建一个工作簿
        Workbook workbook = new HSSFWorkbook();// 03版
        // 2.创建一个工作表
        Sheet sheet = workbook.createSheet("张雨晗"); // 给表命名
        // 3.创建一个行(1,1)最左上角的格子
        Row row1 = sheet.createRow(0);
        // 4.创建一个单元格(1,1)最左上角的格子
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("超人");
        // (1,2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue("钢铁之躯");

        // 第二行
        Row row2 = sheet.createRow(1);
        //(2,1)
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        //(2,2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);

        // 生成一张表 ,03版本就是以.xls结尾
        FileOutputStream outputStream = new FileOutputStream(path + "03版本的poi生成的表.xls");
        // 输出
        workbook.write(outputStream);

        // 关闭流
        outputStream.close();
        System.out.println("03表生成完毕");
    }

    @Test
    public void testWrite07() throws Exception {

        // 1.创建一个工作簿 07
        Workbook workbook = new XSSFWorkbook();// 03版
        // 2.创建一个工作表
        Sheet sheet = workbook.createSheet("张雨晗"); // 给表命名
        // 3.创建一个行(1,1)最左上角的格子
        Row row1 = sheet.createRow(0);
        // 4.创建一个单元格(1,1)最左上角的格子
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("超人");
        // (1,2)
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue("钢铁之躯");

        // 第二行
        Row row2 = sheet.createRow(1);
        //(2,1)
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("统计时间");
        //(2,2)
        Cell cell22 = row2.createCell(1);
        String time = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        cell22.setCellValue(time);

        // 生成一张表 ,03版本就是以.xlsx结尾
        FileOutputStream outputStream = new FileOutputStream(path + "07版本的poi生成的表.xlsx");
        // 输出
        workbook.write(outputStream);

        // 关闭流
        outputStream.close();
        System.out.println("07表生成完毕");
    }

    @Test
    public void testWrite03BigData() throws IOException {
        //时间
        long begin = System.currentTimeMillis();

        //创建一个工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建一个表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNum = 0; rowNum < 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }


        FileOutputStream outputStream = new FileOutputStream(path + "03利用poi写入大数据.xls");
        workbook.write(outputStream);
        outputStream.close();

        System.out.println("over");

        long end = System.currentTimeMillis();

        // 输出经过的时间
        System.out.println((double) (end - begin) / 1000);

    }

    // 写入同量的数据，07版比03版耗时更长，但是07版可以写更大量的数据
    // 优化：缓存。利用SXSSF对象对07版进行优化
    @Test
    public void testWrite07BigData() throws IOException {
        //时间
        long begin = System.currentTimeMillis();

        //创建一个工作簿
        Workbook workbook = new XSSFWorkbook();
        //创建一个表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNum = 0; rowNum < 200000; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }


        FileOutputStream outputStream = new FileOutputStream(path + "07利用poi写入大数据.xlsx");
        workbook.write(outputStream);
        outputStream.close();

        System.out.println("over");

        long end = System.currentTimeMillis();

        // 输出经过的时间
        System.out.println((double) (end - begin) / 1000);

    }

    @Test
    public void testWrite07BigDataSuper() throws IOException {
        //时间
        long begin = System.currentTimeMillis();

        //创建一个工作簿
        Workbook workbook = new SXSSFWorkbook();
        //创建一个表
        Sheet sheet = workbook.createSheet();
        //写入数据
        for (int rowNum = 0; rowNum < 200000; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }


        FileOutputStream outputStream = new FileOutputStream(path + "07利用poi写入大数据加速版.xlsx");
        workbook.write(outputStream);
        outputStream.close();

        // 清除临时文件
        ((SXSSFWorkbook) workbook).dispose();

        System.out.println("over");

        long end = System.currentTimeMillis();

        // 输出经过的时间
        System.out.println((double) (end - begin) / 1000);
    }
}
