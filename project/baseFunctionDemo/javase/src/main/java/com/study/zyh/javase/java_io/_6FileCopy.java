package com.study.zyh.javase.java_io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName _6FileCopy
 * @Description 综合运用文件的字节输入流和字节输出流，实现文件的拷贝
 * @Author Zhangyuhan
 * @Date 2021/8/26
 * @Version 1.0
 */
public class _6FileCopy {

    @Test
    public void copyFile() {
        // 思路分析：
        // 1.创建文件的输入流，讲文件读入到程序
        // 2.创建文件的输出流，将读取到的文件数据，写入到指定的文件
        // 注意：在完成程序时，应该是读取部分数据就写入到指定文件。通过循环完成
        String sourceFilePath = "D:\\javaTest\\source.jpg";
        String targetFilePath = "D:\\javaTest\\target.jpg";
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        // 每次循环读取1k(即1k1k的写入)
        byte[] b = new byte[1024];
        int readLen = 0;
        try {
            fileInputStream = new FileInputStream(sourceFilePath);
            fileOutputStream = new FileOutputStream(targetFilePath);
            while ((readLen = fileInputStream.read(b)) != -1) {
                // 边读边写
                // 注意：一定要使用这个方法，如果不加偏移量，那么拷贝出的文件大概率内容错误
                fileOutputStream.write(b, 0, readLen);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭输入流和输出流，释放资源
            try {
                if (fileInputStream != null) {
                    fileOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
