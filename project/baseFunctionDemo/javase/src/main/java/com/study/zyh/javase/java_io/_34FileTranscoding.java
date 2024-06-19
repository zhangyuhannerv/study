package com.study.zyh.javase.java_io;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 文件转码,启用，转到baseFunctionDemo里去了
 */
@Deprecated
public class _34FileTranscoding {

    public static void main(String[] args) throws Exception {

        FileInputStream fis = new FileInputStream("/Users/zhangyuhan/Work/WorkProject/3B_czjc/dataFile/20230209/AXMS-xingong-mudanyuan-09022023-160809-1-(31-52).txt");
        FileOutputStream fos = new FileOutputStream("/Users/zhangyuhan/Work/WorkProject/3B_czjc/dataFile/20230209/下行.txt");
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buff = new byte[4096];
        while (fis.read(buff) != -1) {
            out.write(buff);
        }

        // 这里是读取gbk，写入入utf-8
        fos.write(out.toString("GBK").getBytes("UTF-8"));

        fis.close();
        out.close();
        fos.close();
    }
}
