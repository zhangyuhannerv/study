package com.study.zyh.javase.java_io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @ClassName _25PropertiesClassReadFile
 * @Description Properties类读文件
 * 文件要求：
 * 键=值
 * 键值对不要有空格
 * 值不需要用引号引起来，值的默认类型是String
 * 常用方法:
 * load()加载配置文件键值到Properties对象
 * list()将数据显示到指定设备（流对象）
 * getProperty(key)：指定键获得值
 * setProperty(key,value):设置键值到Properties对象（没有key会新增)
 * store()将Properties中的键值对存储到配置文件中(原先存在则覆盖)，在idea中，保存信息到配置文件时，如果含有中文，会存储为unicode对应的码值
 * @Author Zhangyuhan
 * @Date 2021/9/28
 * @Version 1.0
 */
public class _25PropertiesClassReadFile {
    // 读取mysql.properties文件
    public static void main(String[] args) {
        // 1.创建Properties对象
        Properties properties = new Properties();
        // java8以后这种方式定义的流会自动关闭(新的写法)
        try (FileReader fileReader = new FileReader("java_io/mysql.properties")) {
            // 2.加载配置文件
            properties.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 3.把k-v显示到控制台
        properties.list(System.out);
        // 4.根据key获取对应的值
        // get()返回Object对象，如需用到属性，可以向下转型成String(强转)
        System.out.println("用户:" + properties.get("user"));
        System.out.println("密码:" + properties.get("pwd"));
    }
}
