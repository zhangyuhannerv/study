package java_io;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @ClassName _7FileReader
 * @Description 文件字符流读取(文本文件用字符流比较好 ， 二进制文件 ( 音视频) 等用字节流比较好)
 * @Author Zhangyuhan
 * @Date 2021/9/2
 * @Version 1.0
 */
public class _7FileReader {
    // 需求，读取文件的内容，打印到控制台

    // 方案1：读取单个字符
    public static void main(String[] args) {
        String filePath = "D:\\javaTest\\story.txt";
        FileReader fileReader = null;
        int ch;
        try {
            fileReader = new FileReader(filePath);
            // 这里要注意 read()方法返回的是int类型
            // 循环读取，使用read，单个字符读取
            while ((ch = fileReader.read()) != -1) {
                // 因为返回的是int型，所以可以用char强转一下
                System.out.print((char) ch);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    // 方案2：通过字符数组读取
    public void fileRead01() {
        String filePath = "D:\\javaTest\\story.txt";
        FileReader fileReader = null;
        char[] buf = new char[8];
        int readLen = 0;
        try {
            fileReader = new FileReader(filePath);
            // 循环读取，每次读取8个字符，返回的是实际读取到的字符数量
            // 如果返回-1，说明到文件的结束了
            while ((readLen = fileReader.read(buf)) != -1) {
                // 因为返回的是int型，所以可以用char强转一下
                // 最后一次读取到的长度可能不是8，所以必须要加偏移量
                System.out.print(new String(buf, 0, readLen));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
