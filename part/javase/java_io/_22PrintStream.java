package java_io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @ClassName _22PrintStream
 * @Description 打印流包含PrintStream（字节流）和PrintWriter(字符流),打印流只有输出流，没有输入流
 * @Author Zhangyuhan
 * @Date 2021/9/27
 * @Version 1.0
 */
public class _22PrintStream {
    // 演示printStream字节打印流
    public static void main(String[] args) {
        PrintStream printStream = null;

        // 默认情况下PrintStream打印数据的位置是标准输出，即显示器
        printStream = System.out;// 最经典的用法

        printStream.println("hello");
        /**
         *     public void print(String s) {
         *         if (s == null) {
         *             s = "null";
         *         }
         *         write(s);
         *     }
         *     因为println()底层是write()方法，所以我们可以直接调用write()来打印(输出)
         */
        try {
            // 本质和print()是一样的
            printStream.write("张雨晗".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        printStream.close();


        // 我们可以修改打印流输出的位置或者设备
        try {
            // 修改到了系统默认的打印输出位置到文件中
            System.setOut(new PrintStream("d:\\javaTest\\print.txt"));
            System.out.println("到文件中：hello，你好啊");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
