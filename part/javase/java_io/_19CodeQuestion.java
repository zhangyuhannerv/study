package java_io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

/**
 * @ClassName _19CodeQuestion
 * @Description 演示中文乱码问题
 * @Author Zhangyuhan
 * @Date 2021/9/26
 * @Version 1.0
 */
public class _19CodeQuestion {
    public static void main(String[] args) {
        String filePath = "d:\\javaTest\\note.txt";
        // 思路
        // 1.创建字符输入流 BufferedReader[处理流]
        // 2.使用BufferedReader读取note.txt
        // 3.默认情况下读取文件是按照"utf-8"编码来读取的
        // 4.文件是按'ANSI'编码的(补充说明：ansi代表国标码，不同的国家有不同的国标,
        // 一般是根据系统的语言选择来选择不同的编码，比如在简体中文的操作系统下，ansi就是gbk
        // 在日文操作系统下，ANSI 编码代表 JIS 编码)
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String str = bufferedReader.readLine();
            System.out.println("读取到的内容是" + str);
            // 此时读取到的内容会发生乱码
            // 原因：文件按照gbk编码，读取按照utf-8编码
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
