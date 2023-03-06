package com.zh.util.fileOperation;

import java.io.*;

public class FileCopy {
    public static void copyFile(File sourceFile, File targetFile, boolean apppend, boolean charOrString) {
        if (charOrString) {
            // 字符流copy
            copyFile1(sourceFile, targetFile, apppend);
        } else {
            // 字节流copy
            copyFile2(sourceFile, targetFile, apppend);
        }
    }

    public static void copyFile1(File sourceFile, File targetFile, boolean append) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        String text = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(sourceFile));
            bufferedWriter = new BufferedWriter(new FileWriter(targetFile, append));
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

    public static void copyFile2(File sourceFile, File targetFile, boolean append) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        char[] ch = new char[1024];
        int len = 0;

        try {
            bufferedReader = new BufferedReader(new FileReader(sourceFile));
            bufferedWriter = new BufferedWriter(new FileWriter(targetFile, append));
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
