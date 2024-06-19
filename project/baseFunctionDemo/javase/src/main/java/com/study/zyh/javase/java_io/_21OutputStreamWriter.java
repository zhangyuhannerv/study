package com.study.zyh.javase.java_io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @ClassName _21OutputStreamWriter
 * @Description 演示使用OutputStreamWriter转换流写入特定编码的文本文件
 * @Author Zhangyuhan
 * @Date 2021/9/26
 * @Version 1.0
 */
public class _21OutputStreamWriter {
    public static void main(String[] args) {
        String filePath = "d:\\javaTest\\note3.txt";
        BufferedWriter bufferedWriter = null;
        try {
            // bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            // fileWriter默认会写入没有bom的utf-8编码的文件，此时记事本会显示编码为ANSI AS UTF-8
            // bufferedWriter.write("你好啊");

            // 手动指定写入utf-8编码格式的话还是不带bom头，
            // 网上说utf-8推荐不带bom头，因为其具有自描述性
            // 不带bom头的utf-8虽然是标准的，但是在win下的excel里可能会引起一下乱码问题
            // 因为win下的excel是用bom识别编码的。目前没遇到过该问题，所以这个excel乱码的问题可以先不用管
            // bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8));
            // bufferedWriter.write("你好啊");

            // 在中文操作系统下还是推荐使用gbk吧
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "gbk"));
            bufferedWriter.write("你好啊");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
