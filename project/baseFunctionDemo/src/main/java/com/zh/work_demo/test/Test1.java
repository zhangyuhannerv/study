package com.zh.work_demo.test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @ClassName Test1
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/10/19
 * @Version 1.0
 */
public class Test1 {
    public static void main(String[] args) {
        String in = "C:\\Users\\13551\\Desktop\\车内里程\\车内里程\\20191211呼和浩特地铁1号线平稳性1#DT1_0.sts";
        String out = "C:\\Users\\13551\\Desktop\\车内里程.xlsx";

        int len;
        byte[] tmp = new byte[4];
        int i = 0;

        try (Workbook wb = new XSSFWorkbook();
             DataInputStream inputStream = new DataInputStream(new FileInputStream(in));
             FileOutputStream os = new FileOutputStream(out)) {

            // 创建一个工作区
            Sheet sheeet = wb.createSheet("里程");

            int colNum = 0;
            int rowNum = 0;
            Row row = sheeet.createRow(rowNum);

            while ((len = inputStream.read(tmp)) > 0) {
                i++;
/*                if (i == 63676) {
                    break;
                }*/
                ByteBuffer buffer = ByteBuffer.wrap(tmp);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                double a = Double.parseDouble(String.format("%.3f", buffer.getFloat()));

                if (colNum >= 500) {
                    colNum = 0;
                    rowNum++;
                    row = sheeet.createRow(rowNum);
                }
                row.createCell(colNum).setCellValue(a);
                colNum++;
            }
            wb.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

/*        String filePath = out;
        String len;
        StringBuffer str = new StringBuffer("");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            while ((len = bufferedReader.readLine()) != null) {
                str.append(len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] dataArr = str.toString().split(",");
        System.out.println(dataArr.length);*/
    }
}
