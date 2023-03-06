package com.zh.util.fileOperation;

import java.io.*;

public class FileTransferEncoding {
    public static void main(String[] args) {
        try {
            transferEncoding1("/我是gbk文件", "GBK", "/我是UTF-8文件", "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sourceFilePath   原文件路径
     * @param sourceFileCoding 源文件编码
     * @param targetFilePath   目标文件路径
     * @param targetFileCoding 目标文件编码
     */
    public static void transferEncoding1(String sourceFilePath, String sourceFileCoding, String targetFilePath, String targetFileCoding) throws IOException {
        //1.创建InputStreamReader对象,构造方法中传递字节输入流和指定的编码表名称GBK
        InputStreamReader isr = new InputStreamReader(new FileInputStream(sourceFilePath), sourceFileCoding);
        //2.创建OutputStreamWriter对象,构造方法中传递字节输出流和指定的编码表名称UTF-8
        java.io.OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(targetFilePath), targetFileCoding);
        //3.使用InputStreamReader对象中的方法read读取文件
        int len = 0;
        while ((len = isr.read()) != -1) {
            //4.使用OutputStreamWriter对象中的方法write,把读取的数据写入到文件中
            osw.write(len);
        }
        //5.释放资源
        osw.close();
        isr.close();
    }
}
