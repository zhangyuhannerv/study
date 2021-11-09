package com.zh.work_demo;

import com.zh.FileCut.FileCut;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @ClassName _5获取图片的长宽
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/11/9
 * @Version 1.0
 */
public class _5获取图片的长宽 {
    @Test
    public void test() {
        String sourceDir = "D:\\wendang\\图片\\动漫";
        String verDir = "D:\\wendang\\图片\\动漫\\竖屏";
        String horDir = "D:\\wendang\\图片\\动漫\\横屏";

        File file = new File(sourceDir);
        File[] files = file.listFiles();
        for (File tempFile : files) {
            if (tempFile.isDirectory()) {
                continue;
            }

            int[] imgWidthAndHeight = getImgWidthAndHeight(tempFile);
            if (imgWidthAndHeight[0] > imgWidthAndHeight[1]) {// 宽度大于高度
                boolean cut = FileCut.cut(tempFile, horDir);
                if (cut) {
                    System.out.println("移动" + tempFile.getName() + "成功");
                }
            } else {
                boolean cut = FileCut.cut(tempFile, verDir);
                if (cut) {
                    System.out.println("移动" + tempFile.getName() + "成功");
                }
            }
        }
    }

    /**
     * 获取图片宽度
     *
     * @param file 图片文件
     * @return 宽度
     */
    public static int getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getWidth(null); // 得到源图宽
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 获取图片高度
     *
     * @param file 图片文件
     * @return 高度
     */
    public static int getImgHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取图片的宽度和高度
     *
     * @param file
     * @return
     */
    public static int[] getImgWidthAndHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int[] res = {-1, -1};
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            res[0] = src.getWidth(null);// 得到图片的宽度
            res[1] = src.getHeight(null); // 得到源图高度
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }
}
