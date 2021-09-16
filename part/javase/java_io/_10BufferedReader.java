package java_io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @ClassName _10BufferedReader
 * @Description 字符处理流(包装流)BufferedReader, 尽量用其处理文本文件
 * @Author Zhangyuhan
 * @Date 2021/9/16
 * @Version 1.0
 */
public class _10BufferedReader {
    // 演示BufferedReader的使用

    public static void main(String[] args) {
        String filePath = "d:\\javaTest\\note.txt";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            // 读取演示
            String line;//按行读取的文本变量
            // readLine()时扩展方法，节点流不具备
            // readLine()是按行读取文件，当返回是null时，表示文件读取完毕
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    // 在关闭处理流时，只需要关闭外层流（处理流：bufferedReader）即可，因为底层会自动的关闭节点流。这里的节点流就是FileReader
                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
