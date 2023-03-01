package com.zh.work_demo;

import com.zh.fileOperation.FileCut;
import org.junit.Test;

import java.io.File;

/**
 * @ClassName _7wallpaperRemove
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/1/3
 * @Version 1.0
 */
public class _7wallpaperRemove {
    private String dirPath = "D:\\wendang\\图片\\碧蓝航线\\立绘 皮肤";
    // private String dirPath = "D:\\wendang\\图片\\碧蓝航线\\过场图";
    private String ver = "D:\\wendang\\图片\\碧蓝航线\\竖屏";// 竖屏
    private String hor = "D:\\wendang\\图片\\碧蓝航线\\横屏";// 横屏

    @Test
    public void run() {
        fun(new File(dirPath));
    }

    public void fun(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                fun(file1);
            }
        } else {
            int[] imgWidthAndHeight = _5获取图片的长宽.getImgWidthAndHeight(file);
            if (imgWidthAndHeight[0] > imgWidthAndHeight[1]) {// 横屏
                FileCut.cut(file, hor);
            } else {// 竖屏
                FileCut.cut(file, ver);
            }
            System.out.println("移动" + file.getName() + "成功");
        }
    }
}
