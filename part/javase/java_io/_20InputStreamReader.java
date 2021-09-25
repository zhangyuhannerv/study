package java_io;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @ClassName _20InputStreamReader
 * @Description 演示使用InputStreamReader转换流解决乱码问题
 * @Author Zhangyuhan
 * @Date 2021/9/26
 * @Version 1.0
 */
public class _20InputStreamReader {
    // 关键:
    // InputStreamReader能将FileInputStream字节流转为字符流，并指定编码
    public static void main(String[] args) {
        String filePath = "d:\\javaTest\\note.txt";

        BufferedReader bufferedReader = null;

        try {
            // 解读
            // 1.将FileInputStream转成InputStreamReader
            // 2.指定了编码 gbk
            // 3.把InputStreamReader传入BufferedReader
            bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath), "gbk"));
            String str = bufferedReader.readLine();
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
