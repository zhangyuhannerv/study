package com.zh.unzipDemo;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @ClassName UnzipDemo1
 * @Description java解压文件
 * @Author Zhangyuhan
 * @Date 2021/10/26
 * @Version 1.0
 */
public class UnzipDemo1 {
    /**
     * 无论压缩文件下有多少层级，所有解压后的文件都统一放在outFileDir文件夹下，且只保留压缩的文件，压缩的文件夹不保留
     *
     * @param inFilePath 压缩文件路径
     * @param outDirPath 解压目录的文件夹
     */
    public static boolean unzip(String inFilePath, String outDirPath) {

        File destFile = new File(outDirPath);

        if (destFile.isFile()) {
            return false;
        }

        if (!destFile.exists()) {
            destFile.mkdirs();
        }


        File sourceFile = new File(inFilePath);
        String fileName = sourceFile.getName();
        String fileType = fileName.substring(fileName.lastIndexOf("."));

        if (!".zip".equals(fileType)) {
            return false;
        }


        // 一次读取1k
        byte[] buff = new byte[1024];
        int readLen = 0;

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(inFilePath), Charset.forName("GBK"));
             ZipFile zipFile = new ZipFile(sourceFile, Charset.forName("GBK"))) {

            ZipEntry entry = null;
            while (((entry = zin.getNextEntry()) != null)) {//如果entry不为空，并不在同一个目录下

                if (entry.isDirectory()) {
                    continue;
                }

                String entryName = entry.getName().substring(entry.getName().lastIndexOf("/") + 1);

                File tmp = new File(outDirPath + "/" + entryName);//解压出的文件路径
                if (!tmp.exists()) {//如果文件不存在
                    File parentDir = tmp.getParentFile();

                    if (!parentDir.exists()) {
                        parentDir.mkdirs();
                    }

                    try (OutputStream os = new FileOutputStream(tmp);//将文件目录中的文件放入输出流
                         //用输入流读取压缩文件中制定目录中的文件
                         InputStream in = zipFile.getInputStream(entry)) {

                        while ((readLen = in.read(buff)) != -1) {//如有输入流可以读取到数值
                            os.write(buff, 0, readLen);//输出流写入
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }

                }
                zin.closeEntry();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
