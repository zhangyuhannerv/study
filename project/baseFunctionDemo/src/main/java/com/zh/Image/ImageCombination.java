package com.zh.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.zh.pdf.PdfToImageConverter.*;

public class ImageCombination {
    public static void main(String[] args) {
        try {
            // 图片文件路径
            List<String> imageList = new ArrayList<>();
            for (int page = startPage; page <= endPage; page++) {
                imageList.add(dir + "page" + (page + 1) + ".png");
            }

            // 读取第一张图片以获取宽度和高度
            BufferedImage firstImage = ImageIO.read(new File(imageList.get(0)));
            int combinedWidth = firstImage.getWidth();
            int combinedHeight = firstImage.getHeight() * imageList.size();

            // 创建合成后的图片
            BufferedImage combinedImage = new BufferedImage(combinedWidth, combinedHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = combinedImage.createGraphics();

            int currentY = 0;

            // 循环将每张图片绘制到合成图片中
            for (String imagePath : imageList) {
                BufferedImage img = ImageIO.read(new File(imagePath));
                g2d.drawImage(img, 0, currentY, null);
                currentY += img.getHeight();
            }

            g2d.dispose();

            // 保存合成后的图片
            ImageIO.write(combinedImage, "PNG", new File(dir + "combinedImage.png"));

            System.out.println("Images combined successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

