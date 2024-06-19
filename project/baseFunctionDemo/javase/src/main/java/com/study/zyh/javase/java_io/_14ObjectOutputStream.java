package com.study.zyh.javase.java_io;


import com.study.zyh.javase.bean.Dog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @ClassName _14ObjectOutputStream
 * @Description 演示ObjectOutputStream的使用，完成数据的序列化
 * @Author Zhangyuhan
 * @Date 2021/9/22
 * @Version 1.0
 */
public class _14ObjectOutputStream {
    public static void main(String[] args) {
        // 序列化后，保存的文件格式，不是纯文本，而是按照他的格式来保存。文件名的后缀可以是任意的。推荐dat
        String filePath = "d:\\javaTest\\dog.dat";
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
            // 序列化数据到文件中
            // 如果用write()的话，那么只保存了个值，没有保存数据类型，所以不要用write()
            oos.writeInt(100);// int->Integer,Integer是实现了serializable接口的
            oos.writeBoolean(true);// boolean->Boolean(实现了serializable)
            oos.writeChar('a');// char->Character(实现了serializable)
            oos.writeDouble(9.5);// 同理
            oos.writeUTF("张雨晗");// String(实现了serializable)
            // 保存一个dog对象（Dog类必须实现serializable）
            oos.writeObject(new Dog("张三", 10, "日本", "白色"));

            System.out.println("数据保存完毕(序列化形式保存)");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
