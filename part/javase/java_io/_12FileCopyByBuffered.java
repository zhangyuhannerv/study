package java_io;

import java.io.*;

/**
 * @ClassName _12FileCopyByBuffered
 * @Description 通过处理流BufferedReader和BufferedWriter实现文件的拷贝
 * @Author Zhangyuhan
 * @Date 2021/9/16
 * @Version 1.0
 */
public class _12FileCopyByBuffered {
    public static void main(String[] args) {
        _12FileCopyByBuffered fileCopyByBuffered = new _12FileCopyByBuffered();
        String file1 = "D:\\javaTest\\note.txt";
        String file2 = "D:\\javaTest\\noteCopy1.txt";
        String file3 = "D:\\javaTest\\noteCopy2.txt";
        fileCopyByBuffered.copyFile1(new File(file1), new File(file2));
        fileCopyByBuffered.copyFile2(new File(file1), new File(file3));
    }

    /**
     * 注意:bufferedReader和bufferedWriter是按照字符来操作的，所以不要去操作二进制文件，如果去操作二进制文件，可能会造成文件损坏
     *
     * @param sourceFile
     * @param targetFile
     */
    public void copyFile1(File sourceFile, File targetFile) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        String text = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(sourceFile));
            bufferedWriter = new BufferedWriter(new FileWriter(targetFile));
            while ((text = bufferedReader.readLine()) != null) {
                // 没读取一行就写入
                bufferedWriter.write(text);
                // 写入一行后插个换行符
                // readLine()时读取一行的内容，但是读取到的内容不带换行符
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }

                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyFile2(File sourceFile, File targetFile) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        char[] ch = new char[1024];
        int len = 0;

        try {
            bufferedReader = new BufferedReader(new FileReader(sourceFile));
            bufferedWriter = new BufferedWriter(new FileWriter(targetFile));
            while ((len = bufferedReader.read(ch)) != -1) {
                bufferedWriter.write(ch, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
