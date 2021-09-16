package java_io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName _11BufferedWriter
 * @Description 字节输出处理流BufferedWriter
 * @Author Zhangyuhan
 * @Date 2021/9/16
 * @Version 1.0
 */
public class _11BufferedWriter {
    // 演示BufferedWriter的使用
    public static void main(String[] args) {
        String filePath = "d:\\javaTest\\note.txt";
        BufferedWriter bufferedWriter = null;
        try {
            // 如果想以追加的方式写入内容，在节点流的构造器上加true
            bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
            bufferedWriter.write("ddddddddddddddddddddddddd");
            bufferedWriter.write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            // 建议插入一个换行
            // 这就是插入一个换行的方法(会根据操作系统，判断应该插入什么样的换行符)
            bufferedWriter.newLine();
            bufferedWriter.write("お元気ですか");
            bufferedWriter.newLine();
            bufferedWriter.write("はたしはげんきです");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    // 关闭外层流即可，会自动flush()
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
