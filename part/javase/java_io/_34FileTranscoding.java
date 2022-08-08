package java_io;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 文件转码
 */
public class _34FileTranscoding {

    public static void main(String[] args) throws Exception {

        FileInputStream fis = new FileInputStream("/Users/zhangyuhan/Work/study/document/已购软件.txt");
        FileOutputStream fos = new FileOutputStream("/Users/zhangyuhan/Work/study/document/已购软件1.txt");
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buff = new byte[4096];
        while (fis.read(buff) != -1) {
            out.write(buff);
        }

        // 这里是读取gbk，吸入utf-8
        fos.write(out.toString("GBK").getBytes("UTF-8"));

        fis.close();
        out.close();
        fos.close();
    }
}
