package com.zh.work_demo.test;

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
        String out = "C:\\Users\\13551\\Desktop\\车内里程.txt";

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
                if (i % 32768 == 0) {
                    fw.write("中断");

                    System.out.println(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String filePath = out;
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
        System.out.println(dataArr.length);
    }
}
