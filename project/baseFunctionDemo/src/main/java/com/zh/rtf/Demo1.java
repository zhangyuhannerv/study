package com.zh.rtf;

import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class Demo1 {
    public static final String ROOT_PATH = "/Users/zhangyuhan/Work/WorkProject/gzdt/广州地铁文件/";
    public static final String FIlE_PATH = "/rtf/";
    public static final String IMAGE_PATH = "/img/";
    public static final String WORD_PATH = "/word/";
    public static final String FILE_NAME = "综合报表_广州地铁-22下行18015016(2023-08-17_06_30_02)_20230817_092025_1(1).rtf";

    public static final String FILE = ROOT_PATH + FIlE_PATH + FILE_NAME;
    public static final String WORD_FILE = ROOT_PATH + WORD_PATH + "demo.docx";


    /**
     * 将rtf转为word
     */
    @Test
    public void test0() throws Exception {
        com.aspose.words.Document doc = new com.aspose.words.Document(FILE);
        doc.save(WORD_FILE);
    }


    /**
     * 读取文本和图片
     */
    @Test
    public void test1() {
        String temp = UUID.randomUUID().toString().replace("-", "");
        try ( //获取文件流
              InputStream is = Files.newInputStream(Paths.get(WORD_FILE));
              //获取文件对象
              XWPFDocument doc = new XWPFDocument(is)
        ) {
            // 获取图片内容
//            List<XWPFPictureData> allPictures = doc.getAllPictures();
//            System.out.println(allPictures.size());
//
//            for (XWPFPictureData pic : allPictures) {
//                byte[] bytev = pic.getData();
//                File imgFile = new File(ROOT_PATH + IMAGE_PATH + pic.getFileName());
//                FileOutputStream fos = new FileOutputStream(imgFile);
//                fos.write(bytev);
//                fos.close();
//            }

            //获取表格内容
            List<XWPFTable> tables = doc.getTables();
            List<XWPFPicture> pictures = new ArrayList<>();
            for (int i = 1; i < tables.size(); i++) {
                XWPFTable table = tables.get(i);
                //迭代行，默认从0开始
                for (int j = 2; j < table.getRows().size(); j++) {
                    //当前行
                    XWPFTableRow tr = table.getRow(j);
                    //用于存放一行数据，不需要可以不用
                    StringBuilder rowText = new StringBuilder();
                    //迭代列，默认从0开始
                    for (int x = 0; x < tr.getTableCells().size(); x++) {
                        //取得单元格
                        XWPFTableCell cell = tr.getCell(x);
                        //取得单元格的内容
                        String text = cell.getText();
                        //自己用“ ”区分两列数据，根据自己需求 可以省略
                        if (text != null && !"".equals(text.trim())) {
                            if ("".contentEquals(rowText)) {
                                rowText.append(text);
                            } else {
                                rowText.append(temp).append(text);
                            }
                        }
                    }
                    if (!"".contentEquals(rowText)) {
                        System.out.println(Arrays.toString(rowText.toString().split(temp)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
