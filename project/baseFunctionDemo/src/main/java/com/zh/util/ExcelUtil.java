package com.zh.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * @ClassName ExcelUtil
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/11
 * @Version 1.0
 */
public class ExcelUtil {
    /**
     * （.xlsx）文件后缀
     */
    private static final String XSSF = ".xlsx";
    /**
     * （.xls）文件后缀
     */
    private static final String HSSF = ".xls";


    /**
     * 获取WorkBook对象
     *
     * @param in
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Workbook getWorkBook(InputStream in, String fileName) throws IOException {
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        Workbook wb = null;
        switch (fileType) {
            case XSSF:
                wb = new XSSFWorkbook(in);
                break;
            case HSSF:
                wb = new HSSFWorkbook(in);
        }
        return wb;
    }

    public static String getCellStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }

    /**
     * 这是从别的地方摘取过来的获取原生的cell值
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        if (cell == null) {
            value = "";
            return value;
        }
        DecimalFormat df = new DecimalFormat("#.###");
        CellType cellType = cell.getCellTypeEnum();
        if (cellType == CellType.FORMULA) {
            cellType = cell.getCachedFormulaResultTypeEnum();
        }
        switch (cellType) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                    value = df2.format(cell.getDateCellValue());
                } else {
                    value = df.format(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 将单元格内容转化为字符串
     */
    public static String convertCellValueToString(Cell cell) {
        if (null == cell) {
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:  //字符串
                returnValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                double numericCellValue = cell.getNumericCellValue();
                boolean isInteger = isIntegerForDouble(numericCellValue);
                if (isInteger) {
                    DecimalFormat df = new DecimalFormat("0");
                    returnValue = df.format(numericCellValue);
                } else {
                    returnValue = Double.toString(numericCellValue);
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN: //布尔
                boolean booleanCellValue = cell.getBooleanCellValue();
                returnValue = Boolean.toString(booleanCellValue);
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    /**
     * 判断是否为整数，是返回true，否则返回false.
     */
    public static boolean isIntegerForDouble(Double num) {
        double eqs = 1e-10; //精度范围
        return num - Math.floor(num) < eqs;
    }


}
