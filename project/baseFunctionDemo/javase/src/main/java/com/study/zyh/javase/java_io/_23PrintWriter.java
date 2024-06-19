package com.study.zyh.javase.java_io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName _23PrintWriter
 * @Description 演示PrintWriter的使用
 * @Author Zhangyuhan
 * @Date 2021/9/27
 * @Version 1.0
 */
public class _23PrintWriter {
    public static void main(String[] args) {
        String filePath = "d:\\javaTest\\note4.txt";
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.println("北京，你好");
        printWriter.flush();

        try {
            printWriter = new PrintWriter(new FileWriter(filePath));
            printWriter.println("我不好");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 和其他的输入流一样，如果不close()或者flush()那么是打印不了的
            printWriter.close();
        }
    }
}
