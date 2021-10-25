package java_io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @ClassName _27数据流
 * @Description 数据流：DataInputStream和DataOutputStream,它们都是处理流
 * 目的：读取或写出Java语言的基本数据类型和String类型的数据
 * @Author Zhangyuhan
 * @Date 2021/10/22
 * @Version 1.0
 */
public class _27数据流 {
    public static void main(String[] args) {
        // 将内存中的字符串或者基本数据类型的变量写出到文件中
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("C:\\Users\\13551\\Desktop\\out.dat"));) {
            dos.writeUTF("张三");
            dos.writeInt(23);
            dos.writeBoolean(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 读取数据
        try (DataInputStream dis = new DataInputStream(new FileInputStream("C:\\Users\\13551\\Desktop\\out.dat"))) {
            // 注意：读取不同类型的数据的顺序要与当初写入文件时，保存的数据的顺序一致
            // 如果读的顺序和写的顺序相反会报EOFException
            // System.out.println(dis.readInt());
            System.out.println(dis.readUTF());
            System.out.println(dis.readInt());
            System.out.println(dis.readBoolean());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
