package com.zh.Controller;

import com.zh.work_demo._4readExcel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName Test
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/7/27
 * @Version 1.0
 */
@Controller
public class Test {
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "spring-boot项目启动成功";
    }

    @RequestMapping("/getExcelData")
    @ResponseBody
    public Map<String, List<String>> getExcelData() {
        String filePath = "/excel/1_20210709杭州地铁6号线平稳性_2021_08_28_005001_5S.xlsx";
        return _4readExcel.getExcelData(filePath);
    }
}
