package com.study.zyh.javase.java_io;

import org.junit.Test;

import java.io.File;

/**
 * @ClassName _3DirOperation
 * @Description 对目录的操作
 * @Author Zhangyuhan
 * @Date 2021/8/25
 * @Version 1.0
 */
public class _3DirOperation {

    // 判断文件是否存在，存在即删除
    @Test
    public void test1() {
        String filePath = "D:\\javaTest\\name2.txt";
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } else {
            System.out.println("文件不存在");
        }
    }

    // 判断目录是否存在，如果存在就删除，否则提示不存在
    // 这里我们需要体会到在java编程中，目录也被当作文件
    // 注意：如果目录里有文件或者子目录，那么会删除失败。需要先将目录清空，才可以正确删除该目录
    @Test
    public void test2() {
        String filePath = "D:\\test";
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } else {
            System.out.println("该目录不存在");
        }
    }

    // 判断D:\javaTest\a\b\c目录是否存在，如果存在就提示已经存在，否则就创建
    @Test
    public void test3() {
        String path = "D:\\javaTest\\a\\b\\c";
        File file = new File(path);
        if (file.exists()) {
            System.out.println("该目录已经存在");
        } else {
            if (
                // mkdir是创建一级目录(此时 a，b 都不存在，所以会创建失败)
                // 如果a,b已经存在，那么只需创建c，此时mkdir()是可以成功的
                //     file.mkdir()
                // mkdirs是创建多级目录（也可以创建一级目录）
                    file.mkdirs()
            ) {
                System.out.println("该目录创建成功");
            } else {
                System.out.println("该目录创建失败");
            }
        }
    }
}
