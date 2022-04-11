package com.zh.ExcelDemo;

import com.zh.ExcelDemo.entity.DtoResult;
import com.zh.ExcelDemo.entity.EngineeringCar;
import com.zh.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ExcelImport
 * @Description 利用excel导入数据
 * @Author Zhangyuhan
 * @Date 2021/7/27
 * @Version 1.0
 */
public class ExcelImport {

    /**
     * 代码示例，下面是写在service层的，以后导入并且验证可以根据下面的来写
     *
     * @param file
     * @return
     */
    @Transactional
    public DtoResult importData(MultipartFile file) {
        List<EngineeringCar> saveList = new ArrayList<>();
        boolean res = true;
        String msg = "";
        int rowNum = -1;
        int colNum = -1;
        try (Workbook wb = new XSSFWorkbook(file.getInputStream())) {
            String userId = "111";
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // List<String> existCodeList = this.list().stream().map(EngineeringCar::getSbCode).collect(Collectors.toList());
            List<String> existCodeList = new ArrayList<>();
            List<String> importCodeList = new ArrayList<>();

            Sheet sheet = wb.getSheetAt(0);
            Row row;
            Cell cell;
            String cellValue;
            String name = null;
            String code = null;
            String zt = "";

            ROW:
            for (int i = 2; i < sheet.getLastRowNum() + 1; i++) {
                rowNum = i + 1;
                row = sheet.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    colNum = j + 1;
                    cell = row.getCell(j);
                    cellValue = String.valueOf(ExcelUtil.getCellValue(cell));
                    switch (j) {
                        case 0:// 设备名称
                            name = cellValue;
                            break;
                        case 1:// 设备编号
                            code = cellValue;
                            break;
                        case 2:// 设备状态
                            zt = cellValue;
                            break;
                    }
                }

                EngineeringCar car = new EngineeringCar();
                car.setSbName(name);

                // 校验设备编号
                if (existCodeList.contains(code)) {
                    res = false;
                    msg = "第" + rowNum + "行,编号和已有数据重复";
                    break;
                } else if (importCodeList.contains(code)) {
                    res = false;
                    msg = "第" + rowNum + "行,编号重复";
                    break;
                } else {
                    importCodeList.add(code);
                    car.setSbCode(code);
                }

                // 校验设备状态
                switch (zt) {
                    case "运行":
                        car.setZt(1);
                        break;
                    case "维修":
                        car.setZt(2);
                        break;
                    default:
                        res = false;
                        msg = "第" + rowNum + "行,状态填写错误，只能是运行或者维修";
                        break ROW;
                }

                // 校验通过
                car.setUpdatePer(userId);
                car.setUpdateTime(timestamp);
                saveList.add(car);

            }

            if (!res) {
                return DtoResult.error(msg);
            } else {
                // this.saveBatch(saveList);
                return DtoResult.ok();
            }


        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (rowNum != -1) {
                msg += "第" + rowNum + "行,";
            }

            if (colNum != -1) {
                msg += "第" + colNum + "列,";
            }

            msg += "读取数据失败";
            return DtoResult.error(msg);
        }
    }
}
