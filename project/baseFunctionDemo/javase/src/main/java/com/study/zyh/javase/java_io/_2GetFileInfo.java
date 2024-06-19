package com.study.zyh.javase.java_io;

import org.junit.Test;

import java.io.File;

/**
 * @ClassName _2GetFileInfo
 * @Description 获取文件的信息
 * @Author Zhangyuhan
 * @Date 2021/8/24
 * @Version 1.0
 */
public class _2GetFileInfo {
    // 获取文件的信息
    @Test
    public void info() {
        // 先读取文件对象
        File file = new File("D:\\javaTest\\name2.txt");

        // 调用相应的方法即可得到对应的信息
        System.out.println("文件名字=" + file.getName());

        // 绝对路径
        System.out.println("绝对路径=" + file.getAbsolutePath());

        // 得到文件父级目录
        System.out.println("父级目录=" + file.getParent());

        // 得到文件大小
        System.out.println("父级大小(字节)=" + file.length());

        // 判断文件是否存在
        System.out.println("文件是否存在=" + file.exists());

        // 判断文件是不是一个文件
        System.out.println("是不是一个文件=" + file.isFile());

        // 判断文件是不是一个目录
        System.out.println("是不是一个目录=" + file.isDirectory());
    }
}
