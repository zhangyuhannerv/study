package com.study.zyh.javase.java_io;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName _8FileWriter
 * @Description 文件字符流读出
 * @Author Zhangyuhan
 * @Date 2021/9/2
 * @Version 1.0
 */
public class _8FileWriter {
    // 需求：将风雨过后，定见彩虹写道note.txt中
    public static void main(String[] args) {
        // 创建一个FileWriter
        String filePath = "d:\\javaTest\\note.txt";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, true);
            // 写入单个字符 int
            fileWriter.write('H');
            // 写入字符数组 char[]
            char[] chars = {'a', 'b', 'c'};
            fileWriter.write(chars);
            // 写入指定数组的指定部分 char[] off len
            fileWriter.write("张雨晗牛啊".toCharArray(), 1, 2);
            // 写入指定的字符串 String
            fileWriter.write(" 你好，~");
            // 写入指定字符串的指定部分 String off len
            fileWriter.write(" 上海，天津，", 1, 3);
            // 在数据量大的情况下，可以使用循环操作
            String str = "风雨过后，定见彩虹";
            int step = 4;
            for (int i = 0; i < str.length(); i += step) {
                fileWriter.write(str, i, i + step > str.length() ? str.length() - i : step);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 注意：
            // 1.对于fileWriter,一定要关闭流close()，或者刷新流flush()，才能真正的把数据写入到文件中
            // 区别在于，flush之后还能使用流，close之后流就不能使用了
            // 2.对于不存在的文件，写的时候会自动创建一个新的文件
            // 3.如果文件的路径(父目录或者父父目录)，没有，那么会报系统找不到对应路径的错误,
            // 所以，文件可以不用手动建。但是目录（任何一级）没有，都必须手动先把目录给创建了
            if (fileWriter != null) {
                try {
                    // fileWriter.flush();
                    fileWriter.close();// 关闭文件流等价于flush()加个关闭流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
