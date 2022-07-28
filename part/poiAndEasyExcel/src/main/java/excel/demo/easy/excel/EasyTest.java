package excel.demo.easy.excel;


import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName EasyTest
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/11
 * @Version 1.0
 */
public class EasyTest {
    String path = "D:\\Work\\Study\\poiAndEasyExcel\\";

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    @Test
    public void simpleWrite() {
        // 写法1
        String fileName = path + "easyExcelTest.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        // write(文件名（包含路径），格式类)
        // sheet（表名）
        // dowrite（数据）
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
    }

    @Test
    public void simpleRead() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName = path + "easyExcelTest.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 重点注意读取的逻辑DemoDataListener
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();


    }
}
