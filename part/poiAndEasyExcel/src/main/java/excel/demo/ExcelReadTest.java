package excel.demo;

import com.sun.org.apache.xerces.internal.xs.XSFacet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFEvaluationWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @ClassName ExcelReadTest
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/11
 * @Version 1.0
 */
public class ExcelReadTest {
    String path = "D:\\Work\\Study\\poiAndEasyExcel\\";

    @Test
    public void testRead03() throws Exception {

        // 获取文件流
        FileInputStream fileInputStream = new FileInputStream(path + "03版本的poi生成的表.xls");
        // 1.创建一个工作簿。使用excel能操作的，workbook都能操作
        Workbook workbook = new HSSFWorkbook(fileInputStream);// 03版
        // 2.得到表
        Sheet sheet = workbook.getSheetAt(0);
        // 3.得到行
        Row row = sheet.getRow(0);
        // 4.得到列
        Cell cell = row.getCell(2);

        // 在控制台输出得到的值
        // getStringCellValue()只能获取字符串
        // 读取值的时候一定要注意类型，否则就会读取失败
        // System.out.println(cell.getStringCellValue());

        System.out.println(cell.getNumericCellValue());

        // 关闭流
        fileInputStream.close();
    }

    @Test
    public void testRead07() throws Exception {

        // 获取文件流
        FileInputStream fileInputStream = new FileInputStream(path + "07版本的poi生成的表.xlsx");
        // 1.创建一个工作簿。使用excel能操作的，workbook都能操作
        Workbook workbook = new XSSFWorkbook(fileInputStream);// 07版
        // 2.得到表
        Sheet sheet = workbook.getSheetAt(0);
        // 3.得到行
        Row row = sheet.getRow(0);
        // 4.得到列
        Cell cell = row.getCell(1);

        // 在控制台输出得到的值
        // getStringCellValue()只能获取字符串
        // 读取值的时候一定要注意类型，否则就会读取失败
        System.out.println(cell.getStringCellValue());

        //System.out.println(cell.getNumericCellValue());

        // 关闭流
        fileInputStream.close();
    }

    @Test
    public void testCellType() throws IOException {
        // 获取文件流
        FileInputStream fileInputStream = new FileInputStream(path + "测试读取不同类型的excel表.xlsx");
        // 1.创建一个工作簿。使用excel能操作的，workbook都能操作
        Workbook workbook = new XSSFWorkbook(fileInputStream);// 07版

        Sheet sheet = workbook.getSheetAt(0);
        // 2.获取标题内容(第一行）
        Row rowTitel = sheet.getRow(0);
        if (rowTitel != null) {
            int cellCount = rowTitel.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                Cell cell = rowTitel.getCell(cellNum);
                if (cell != null) {
                    int cellType = cell.getCellType();
                    String cellValue = cell.getStringCellValue();
                    System.out.print(cellValue + "|");
                }
            }
            System.out.println();
        }

        // 3.获取表中的内容
        int rows = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rows; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                // 读取行中的列
                int cellCount = rowTitel.getPhysicalNumberOfCells();
                for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                    System.out.print("[" + (rowNum + 1) + "-" + (cellNum + 1) + "]");
                    Cell cell = row.getCell(cellNum);
                    //匹配列的数据类型
                    if (cell != null) {
                        int type = cell.getCellType();
                        String cellValue = "";

                        switch (type) {
                            case XSSFCell.CELL_TYPE_STRING:// 字符串
                                System.out.print("[String]");
                                cellValue = cell.getStringCellValue();
                                break;
                            case XSSFCell.CELL_TYPE_BOOLEAN:// 布尔
                                System.out.print("[boolean]");
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case XSSFCell.CELL_TYPE_BLANK:// 空
                                System.out.print("[blank]");
                                break;
                            case XSSFCell.CELL_TYPE_NUMERIC:// 数字，日期和普通数字
                                System.out.print("[numeric]");
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 日期
                                    System.out.print("[日期}");
                                    Date date = cell.getDateCellValue();
                                    cellValue = new DateTime(date).toString("yyyy-MM-dd");
                                } else {// 普通数字
                                    //如果不是日期，放值数字过长！
                                    System.out.print("[转换为字符串输出]");
                                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                    cellValue = cell.toString();
                                }
                                break;
                            case XSSFCell.CELL_TYPE_ERROR:
                                System.out.print("[数据类型错误]");
                                break;
                        }
                        System.out.print(cellValue + "\t");
                    }
                }
                System.out.println();
            }
        }
        fileInputStream.close();
    }

    @Test
    public void testFormula() throws IOException {
        // 获取文件流
        FileInputStream fileInputStream = new FileInputStream(path + "测试读取公式.xlsx");
        // 1.创建一个工作簿。使用excel能操作的，workbook都能操作
        Workbook workbook = new XSSFWorkbook(fileInputStream);// 07版

        Sheet sheet = workbook.getSheetAt(0);

        Row row = sheet.getRow(4);
        Cell cell = row.getCell(0);

        // 拿到计算公式
        FormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);

        // 输出单元格的内容
        int type = cell.getCellType();
        switch (type) {
            case Cell.CELL_TYPE_FORMULA:// 公式
                String formula = cell.getCellFormula();
                System.out.println(formula);

                // 计算
                CellValue evaluate = formulaEvaluator.evaluate(cell);
                String cellValue = evaluate.formatAsString();
                System.out.println(cellValue);
                break;
        }


    }
}
