package com.study.zyh.javase.java_io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @ClassName _4FileInputStream
 * @Description 字节输入流(文件 - > 程序)
 * @Author Zhangyuhan
 * @Date 2021/8/25
 * @Version 1.0
 */
public class _4FileInputStream {

    /**
     * 演示读取文件
     * 单个字节的读取，效率很低
     */
    @Test
    public void readFile01() {
        String filePath = "D:\\javaTest\\hello.txt";
        int readData = 0;
        FileInputStream fileInputStream = null;
        try {
            // 创建了fileInputStream对象，用于读取文件
            fileInputStream = new FileInputStream(filePath);
            // read()方法默认读取一个字节，如果到达文件末尾，那么返回-1
            // 文档上说返回下一个字节，但是我个人理解还是返回当前读到的字节
            while ((readData = fileInputStream.read()) != -1) {
                System.out.print((char) readData);// 转成char显示
                // System.out.println(readData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭文件流释放资源
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 演示读取文件
     * 使用read(byte[] b)读取文件，来提高效率
     */
    @Test
    public void readFile02() {
        // 如果路径不存在该文件，那么会报文件找不到的异常
        String filePath = "D:\\javaTest\\hello.txt";
        FileInputStream fileInputStream = null;
        // 一次读取8个字节
        byte[] buff = new byte[8];
        int readLen = 0;
        try {
            // 创建了fileInputStream对象，用于读取文件
            fileInputStream = new FileInputStream(filePath);
            // read()方法默认读取一个字节，如果到达文件末尾，那么返回-1
            // 此时文件中的8个字节读取到byte[]数组里面了
            // 如果读取正常，返回的是实际读取到的字节数(比如最后读取的字节数可能小于8)
            while ((readLen = fileInputStream.read(buff)) != -1) {
                // 用字节数组构建一个字符串来打印到控制台

                // buff相当于一个队列，每次把最新读到的字节依次从左面往里塞。
                // 因此在最后读到的如果不是8个的话，可能会残余上次剩余的字节
                // 但是我们在构建字符串的时候用了readLen做偏移量，所以实际用到的还是真正读到的字节
                System.out.print(new String(buff, 0, readLen));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭文件流释放资源
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
