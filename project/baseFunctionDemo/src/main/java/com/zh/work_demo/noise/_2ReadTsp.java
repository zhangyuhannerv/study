package com.zh.work_demo.noise;

import org.junit.Test;

import java.io.*;

/**
 * @ClassName _2ReadTsp
 * @Description 读取噪声描述文件
 * @Author Zhangyuhan
 * @Date 2021/10/18
 * @Version 1.0
 */
public class _2ReadTsp {
    @Test
    public void test() {
        // String rootPath = _2ReadTsp.class.getResource("/").getPath();
        // readTsp(rootPath + "/noise/20191116呼和1号线梯形轨枕直线1#1.tsp");
        String filePath = "C:\\Users\\13551\\Desktop\\车内里程上传文件\\20191211呼和浩特地铁1号线平稳性1#DT1_0.tsp";
        readTsp(filePath);
    }

    public static TspBean readTsp(String in) {
        int line1 = 1;// 第一行提取频率和单位（第一个是频率。最后一个是单位）
        int line2 = 2;// 时间描述文件第二行可以提取名称
        int line11 = 11;// 时间描述文件的第10行可以提取初始时间

        String line;
        int lineIndex = 0;

        TspBean tspBean = new TspBean();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(in), "gbk"))) {
            while ((line = bufferedReader.readLine()) != null) {
                lineIndex++;
                // 第一行：频率，单位，类型
                if (lineIndex == line1) {
                    String[] strArr = line.split(",");

                    if (strArr[0].contains(".")) {
                        tspBean.setFrequency(Integer.parseInt(strArr[0].substring(0, strArr[0].indexOf("."))));
                    } else {
                        tspBean.setFrequency(Integer.parseInt(strArr[0]));
                    }

                    tspBean.setUnit(strArr[strArr.length - 1].replaceAll("\"", ""));

                    if ("km".equals(tspBean.getUnit())) {
                        tspBean.setType("mileage");
                        break;
                    } else {
                        tspBean.setType("time");
                    }
                }

                // 第一行：名称
                if (lineIndex == line2) {
                    tspBean.setName(line);
                }

                // 第十行：起始时间
                if (lineIndex == line11) {
                    tspBean.setStartTime(line);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(tspBean);
        return tspBean;
    }
}
