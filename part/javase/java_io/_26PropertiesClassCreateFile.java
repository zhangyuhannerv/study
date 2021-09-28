package java_io;

import java.io.*;
import java.util.Properties;

/**
 * @ClassName _26PropertiesClassCreateFile
 * @Description 使用Properties类来创建配置文件
 * @Author Zhangyuhan
 * @Date 2021/9/28
 * @Version 1.0
 */
public class _26PropertiesClassCreateFile {
    public static void main(String[] args) {
        // Properties的父类是HashTable,底层就是HashTable的放入键值的方法

        // 创建对象并赋值
        // 如果该文件没有key,就是创建新的配置项
        // 如果该文件有key，就是替换（修改）
        Properties properties = new Properties();
        properties.setProperty("charset", "utf-8");
        properties.setProperty("user", "张");
        properties.setProperty("pwd", "888888");


        // 将k-v存储进文件中(字符流，k-v会以utf-8保存),其中注解以unicode保存
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("java_io/new.properties"))) {
            properties.store(bufferedWriter, "--注解--");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 将k-v存储进文件中(字节流，k-v会以unicode编码保存),其中注解以unicode保存
/*        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("java_io/new.properties"))) {
            properties.store(bufferedOutputStream, "--注解--");
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        System.out.println("保存配置文件成功");
    }
}
