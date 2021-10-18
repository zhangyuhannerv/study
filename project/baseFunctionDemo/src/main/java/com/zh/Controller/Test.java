package com.zh.Controller;

import com.zh.work_demo._4readExcel;
import com.zh.work_demo.noise._3CallNoiseAlgo;
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

    @RequestMapping("/parseNoiseData")
    @ResponseBody
    public boolean parseNoiseData() {
        /*事实证明写在resources下的algo.py无法直接调用*/
        // String algoPath = Thread.currentThread().getContextClassLoader().getResource("noise/algo/a*/lgo.py").getPath();
        // _3CallNoiseAlgo.callNoiseAlgo(algoPath, "C:/Users/13551/Desktop/", "20191116呼和1号线梯形轨枕直线");

        /*必须得写在绝对路径下的.py文件才能直接调用*/
        _3CallNoiseAlgo.callNoiseAlgo("C:/Users/13551/Desktop/algo.py", "C:/Users/13551/Desktop/", "20191116呼和1号线梯形轨枕直线");
        return true;
    }
}
