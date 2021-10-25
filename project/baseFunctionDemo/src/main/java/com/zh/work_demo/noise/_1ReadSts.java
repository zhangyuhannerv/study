package com.zh.work_demo.noise;


import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * ywh
 *
 * @ClassName: ReadData
 * @Description: 读取噪声数据文件
 * @Author Zhangyuhan
 * @date: 2021/10/9 17:49
 */

public class _1ReadSts {
    @Test
    public void test() {
        String rootPath = _1ReadSts.class.getResource("/").getPath();
/*        readSts(rootPath + "noise/20191116呼和1号线梯形轨枕直线1#1.sts",
                rootPath + "noise/data/data.txt");*/

        readSts("C:\\Users\\13551\\Desktop\\车内里程\\车内里程\\20191211呼和浩特地铁1号线平稳性1#DT1_0.sts",
                "C:\\Users\\13551\\Desktop\\里程.txt");
    }

    public static void readSts(String in, String out) {
/*
        File parentFileDir = new File(out.substring(0, out.lastIndexOf("/")));
        if (!parentFileDir.exists()) {
            parentFileDir.mkdirs();
        }
*/

        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(in));
             FileWriter fw = new FileWriter(out)) {

            int len = 4;
            int i = 0;
            while (len > 0) {
                i++;
                byte[] tmp = new byte[4];
                len = inputStream.read(tmp);
                if (len <= 0) {
                    System.out.println("read finish or have bug.");
                    break;
                }
                ByteBuffer buffer = ByteBuffer.wrap(tmp);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                double a = Double.parseDouble(String.format("%.3f", buffer.getFloat()));
                fw.write(a + ",");
                //2048是从解释文件中获取的频率
                if (i % 2048 == 0) {
                    fw.write("\n");

                    System.out.println(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
