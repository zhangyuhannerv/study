package java_io;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @ClassName _16InputAndOutput
 * @Description 标准输入流和标准输出流的刨析
 * @Author Zhangyuhan
 * @Date 2021/9/24
 * @Version 1.0
 */
public class _16InputAndOutput {
    public static void main(String[] args) {
        // System类的 public final static InputStream in = null;
        // System.in的编译类型是InputStream
        // System.in的运行类型是BufferedInputStream
        // System.in表示标准输入，默认为键盘
        System.out.println(System.in.getClass()); // class java.io.BufferedInputStream


        // System类的 public final static PrintStream out = null;
        // System.out的编译类型是PrintStream(打印流)
        // System.out的运行类型是PrintStream(打印流)
        // System.out表示标准输出，默认为显示器
        System.out.println(System.out.getClass());// class java.io.PrintStream

        System.out.println("hello,章三");

        // scanner会从键盘获取数据
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入内容");
        String next = scanner.next();
        System.out.println("我输入的是" + next);
    }
}
