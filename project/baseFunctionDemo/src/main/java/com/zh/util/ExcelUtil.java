package com.zh.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.FORMULA;

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
        if (cellType == FORMULA) {
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
}
