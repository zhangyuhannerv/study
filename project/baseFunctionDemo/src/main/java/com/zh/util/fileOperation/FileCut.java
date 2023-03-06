package com.zh.util.fileOperation;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @ClassName FileCut
 * @Description 图片的剪切
 * @Author Zhangyuhan
 * @Date 2021/11/9
 * @Version 1.0
 */
public class FileCut {
    @Test
    public void test() {
        System.out.println(cut(new File("D:\\wendang\\图片\\pixiv\\ATDAN-竖屏\\56480020_p0.jpg"),
                "D:\\wendang\\图片\\pixiv\\ATDAN-横屏"));
    }

    public static boolean cut(File file, String destDir) {
        String fileName = file.getName();
        byte[] bytes = new byte[1024];
        int readLen = -1;
        try (FileInputStream in = new FileInputStream(file);
             FileOutputStream out = new FileOutputStream(destDir + "/" + fileName)) {
            while ((readLen = in.read(bytes)) != -1) {
                out.write(bytes, 0, readLen);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.delete();
    }
}
