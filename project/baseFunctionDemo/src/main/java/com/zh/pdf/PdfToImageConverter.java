package com.zh.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PdfToImageConverter {
    public static final int startPage = 23;
    public static final int endPage = 37;
    public static final String dir = "/Users/zhangyuhan/Downloads/";

    public static void main(String[] args) {
        try {
            // 指定PDF文件路径
            String pdfFilePath = dir + "N３.pdf";

            // 载入PDF文档
            PDDocument document = PDDocument.load(new File(pdfFilePath));

            // 创建PDF渲染器
            PDFRenderer renderer = new PDFRenderer(document);

            // 循环处理每一页
//            for (int page = 0; page < document.getNumberOfPages(); ++page) {
            for (int page = 23; page <= 37; ++page) {
                // 渲染页面为BufferedImage
                BufferedImage image = renderer.renderImageWithDPI(page, 300, ImageType.RGB);

                // 生成图片文件名
                String imageFileName = dir + "page" + (page + 1) + ".png";

                // 将BufferedImage保存为图片文件
                ImageIO.write(image, "PNG", new File(imageFileName));

                System.out.println("Page " + (page + 1) + " converted to " + imageFileName);
            }

            // 关闭PDF文档
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
