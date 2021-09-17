package java_io;

import java.io.*;

/**
 * @ClassName _13FileCopyByBufferedInputStreamAndOutputStream
 * @Description 通过处理流BufferedInputStream和BufferedOutputStream实现文件的拷贝
 * @Author Zhangyuhan
 * @Date 2021/9/16
 * @Version 1.0
 */
public class _13FileCopyByBufferedInputStreamAndOutputStream {
    public static void main(String[] args) {
        _13FileCopyByBufferedInputStreamAndOutputStream fileCopyByBufferedInputStreamAndOutputStream = new _13FileCopyByBufferedInputStreamAndOutputStream();
        String file1 = "D:\\javaTest\\source.jpg";
        String file2 = "D:\\javaTest\\sourceCopy.jpg";
        fileCopyByBufferedInputStreamAndOutputStream.copyFile1(new File(file1), new File(file2));
    }

    // 音视频，图片等其他二进制文件的拷贝
    // 字节流可以操作二进制文件。它也可以操作文本文件。只是建议用字符流操作文本文件，因为效率更高，效果更好
    public void copyFile1(File sourceFile, File targetFile) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            // FileInputStream是InputStream的字类
            bis = new BufferedInputStream(new FileInputStream(sourceFile));
            bos = new BufferedOutputStream(new FileOutputStream(targetFile));

            byte[] buff = new byte[1024];
            int readLen = 0;

            // 当返回-1时，就表示文件读取完毕
            while ((readLen = bis.read(buff)) != -1) {
                bos.write(buff, 0, readLen);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流，关闭外层的处理流即可，底层会去关闭节点流

                if (bis != null) {
                    bis.close();
                }

                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
