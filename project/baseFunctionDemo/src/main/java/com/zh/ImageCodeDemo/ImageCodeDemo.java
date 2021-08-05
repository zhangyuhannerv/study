package com.zh.ImageCodeDemo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @ClassName ImageCodeDemo
 * @Description 利用java生成简单的图片验证码（本质上是生成一张图片)
 * @Author Zhangyuhan
 * @Date 2021/8/1
 * @Version 1.0
 */
public class ImageCodeDemo {
    // 没有0,o,1,l
    private static String[] strs = {
            "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "m", "n", "p",
            "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z", "2", "3", "4", "5", "6", "7",
            "8", "9"
    };

    public static void main(String[] args) throws IOException {
        // 大的需求：通过java代码的方式生成图片(图片上含有字母，数字，和干扰线)
        // 1.准备画板
        // 2.准备画笔
        // 3.准备一些数据，随机的从数组中获取4个
        // 4.通过画笔把数据画到画板上
        // 5.生成一张真正的图片


        // 图片的宽度和高度（单位:px）
        int w = 150;
        int h = 50;
        // 图片的类型
        int imageType = BufferedImage.TYPE_INT_RGB;

        // 1.画板，jdk中已经提供了这个类 ctrl+p可以查看方法的构造参数
        BufferedImage image = new BufferedImage(w, h, imageType);


        // 2.先获取到画笔对象
        Graphics g = image.getGraphics();
        // 修改图片的颜色
        // 给画笔设置颜色
        g.setColor(Color.YELLOW);
        // 改变背景颜色，通过画一个填充矩形
        g.fillRect(0, 0, w, h);

        // 3.准备数据
        // 设置字体颜色(这里可以传三个参数(使用rgb),这样的话，就可以设置随机颜色)
        g.setColor(Color.red);
        // 设置字体大小
        g.setFont(new Font("楷体", Font.PLAIN, 25));
        // 使用Random类生成随机数字
        Random random = new Random();
        int x = 30;
        int y = 30;
        for (int i = 0; i < 4; i++) {
            // 这个num的值要随机生成的
            int num = random.nextInt(strs.length);// 0~数组最后一位的index之间的整数
            // 每循环一次取一个
            String str = strs[num];
            g.drawString(str, x, y);
            // 每画一次，把x的值变大
            x += 30;
        }

        // 画一点干扰线(10条)
        g.setColor(Color.green);
        for (int i = 0; i < 10; i++) {
            int x1 = random.nextInt(30);
            int y1 = random.nextInt(50);
            int x2 = random.nextInt(30) + 120;
            int y2 = random.nextInt(50);
            g.drawLine(x1, y1, x2, y2);
        }

        //5.生成真正的图片，存到本地的磁盘上
        ImageIO.write(image, "jpg", new File("C:\\Users\\13551\\Desktop\\image_code.jpg"));
    }
}
