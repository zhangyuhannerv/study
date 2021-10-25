package com.zh.work_demo.test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * @ClassName parseStsToTxt
 * @Description 里程sts解析类
 * @Author Zhangyuhan
 * @Date 2021/10/19
 * @Version 1.0
 */
public class parseStsToTxt {
    public static void main(String[] args) {
        String in = "C:\\Users\\13551\\Desktop\\20191211呼和浩特地铁1号线平稳性1#DT1_0.sts";
        String out = "C:\\Users\\13551\\Desktop\\20191211呼和浩特地铁1号线平稳性1#DT1_0.txt";
        // 32768是里程描述文件(.tsp)的频率，不能随便传
        parseStsToTxt(in, out, 32768);
    }

    public static void parseStsToTxt(String inFilePath, String outFilePath, Integer frequency) {
        try (DataInputStream in = new DataInputStream(new FileInputStream(inFilePath));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(outFilePath))) {
            int saveStep = frequency;
            int i = 0;
            byte[] tmp = new byte[4];
            while (in.read(tmp) == 4) {
                i++;
                ByteBuffer buffer = ByteBuffer.wrap(tmp);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                String value = String.format("%.3f", buffer.getFloat());
                if (i == 1 || i % saveStep == 0) {
                    out.writeUTF(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
