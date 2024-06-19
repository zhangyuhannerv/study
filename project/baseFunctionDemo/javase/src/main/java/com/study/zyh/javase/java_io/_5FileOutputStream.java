package com.study.zyh.javase.java_io;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName _5FileOutputStream
 * @Description 字节输出流
 * @Author Zhangyuhan
 * @Date 2021/8/25
 * @Version 1.0
 */
public class _5FileOutputStream {

    /**
     * 演示使用FileOutputStream,将内存中的数据写入到磁盘中
     * 注意：如果该文件不存在，那么就会自动创建文件
     * 注意：write()写入会完全覆盖掉文件原有的内容
     * 注意：如果不需要覆盖原先的内容，在创建文件输出流的时候多传入一个true
     */
    @Test
    public void writeFile01() {
        // 创建FileOutputStream对象
        String filePath = "d:\\javaTest\\1.txt";
        FileOutputStream fileOutputStream = null;

        try {
            // 得到了一个对象
            fileOutputStream = new FileOutputStream(filePath);// 覆盖原有内容
            // fileOutputStream = new FileOutputStream(filePath, true);// 写入的内容追加到原有内容的后面

            // 写入（默认每次写入一个字节)
            // fileOutputStream.write('a');

            // 写入一个字符串
            String str = "lsp,hello world";
            // str.getBytes()可以把字符串转为一个字节数组
            // fileOutputStream.write(str.getBytes());

            // 开发中常用的写入文件内容的方法
            // fileOutputStream.write(byte[] b,int off,int len);
            // 示例：
            fileOutputStream.write(str.getBytes(), 0, str.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
