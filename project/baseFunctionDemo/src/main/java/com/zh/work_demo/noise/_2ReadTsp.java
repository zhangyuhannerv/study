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
        String rootPath = _2ReadTsp.class.getResource("/").getPath();
        readTsp(rootPath + "/noise/20191116呼和1号线梯形轨枕直线1#1.tsp");
    }

    public static void readTsp(String in) {
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(in), "gbk"))) {
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
