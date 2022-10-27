package com.zh.ExcelDemo;

import com.zh.ExcelDemo.entity.Sstjq;
import com.zh.ExcelDemo.entity.SstjqRequestData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @ClassName ExcelExport
 * @Description 利用excel导出数据
 * @Author Zhangyuhan
 * @Date 2021/7/27
 * @Version 1.0
 */
@Controller
public class ExcelExport {

    // poi原生导出数据
    @RequestMapping("/exportData")
    public void exportData(HttpServletResponse response,
                           @RequestBody SstjqRequestData requestData) {
        List<Sstjq> exportList = requestData.getExportList();
        // 利用poi原生导出数据
        try (Workbook wb = new XSSFWorkbook(
                this.getClass().getClassLoader().getResourceAsStream("template/伸缩调节器导入模板.xlsx"));
             OutputStream out = response.getOutputStream()) {
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 0; i < exportList.size(); i++) {
                Sstjq sstjq = exportList.get(i);
                Row row = sheet.createRow(i + 2);
                for (int j = 0; j < 7; j++) {
                    Cell cell = row.createCell(j);
                    switch (j) {
                        case 0:
                            cell.setCellValue(sstjq.getXianbieStr());
                            break;
                        case 1:
                            cell.setCellValue(sstjq.getXingbieStr());
                            break;
                        case 2:
                            cell.setCellValue(sstjq.getStationStr());
                            break;
                        case 3:
                            cell.setCellValue(sstjq.getCode());
                            break;
                        case 4:
                            cell.setCellValue(sstjq.getTypeStr());
                            break;
                        case 5:
                            cell.setCellValue(sstjq.getJdJdLc().doubleValue());
                            break;
                        case 6:
                            cell.setCellValue(sstjq.getBz());
                            break;
                    }
                }
            }
            // 设置头部信息（必须设置）
            response.setHeader(
                    "Content-disposition",
                    "attachment;filename="
                            + new String(("伸缩调节器导出.xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // easyExcel导出数据
}
