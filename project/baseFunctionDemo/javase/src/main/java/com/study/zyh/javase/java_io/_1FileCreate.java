package com.study.zyh.javase.java_io;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName _1FileCreate
 * @Description 演示创建文件
 * @Author Zhangyuhan
 * @Date 2021/8/24
 * @Version 1.0
 */
public class _1FileCreate {
    public static void main(String[] args) {
    }

    @Test
    public void create01() {
        String filePath = "D:\\news1.txt";
        // file此时是内存中的对象
        File file = new File(filePath);
        try {
            // 在磁盘创建真正的文件
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create02() {
        File parentFileDir = new File("D:\\javaTest");
        String fileName = "name2.txt";
        File file = new File(parentFileDir, fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create03() {
        String parentFileDirPath = "D:\\javaTest";
        // 报错：找不到指定的目录，因为没有父目录
        // String fileName = "\\1\\name3.txt";
        String fileName = "name3.txt";
        File file = new File(parentFileDirPath, fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
