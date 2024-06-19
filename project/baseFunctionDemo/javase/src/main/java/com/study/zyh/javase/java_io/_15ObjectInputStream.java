package com.study.zyh.javase.java_io;


import com.study.zyh.javase.bean.Dog;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @ClassName _15ObjectInputStream
 * @Description 数据的反序列化
 * 对象处理流的注意事项
 * 1.写顺序要求一致，（先写个字符串，肯定要先读字符串，否则就报错）
 * 2.要求序列化或者反序列化的对象，必须实现serializable接口
 * 3.序列化的类中建议添加SerialVersionUID,为了提高版本的兼容性（详见bean.Dog）
 * 4.序列化时，默认将里面的所有属性都序列化，除了static和transient修饰的成员（详见bean.Dog）,该成员在序列化化时不会被序列化，在反序列化时会被反序列化成默认值(初始化值)
 * 5.序列化对象时，要求里面属性的类型也需要实现序列化接口（详见bean.Dog和bean.Master）(基本数据类型默认都实现了序列化接口)
 * 6.序列化具有可继承性，也就是说，如果某类已经实现了序列化的接口，则其所有的子类也已经默认实现了序列化的接口
 * @Author Zhangyuhan
 * @Date 2021/9/23
 * @Version 1.0
 */
public class _15ObjectInputStream {
    public static void main(String[] args) {
        // 指定反序列化的文件
        String filePath = "d:\\javaTest\\dog.dat";
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filePath));
            // 读取
            // 解读
            // 1.读取（反序列化）的顺序需要和你保存数据（序列化）的顺序一致
            System.out.println(objectInputStream.readInt());
            System.out.println(objectInputStream.readBoolean());
            System.out.println(objectInputStream.readChar());
            System.out.println(objectInputStream.readDouble());
            System.out.println(objectInputStream.readUTF());
            try {
                // 此时o的编译类型是Object,o的运行类型是Dog,此时无法直接调用Dog类自身的方法
                Object o = objectInputStream.readObject();// 底层Object->Dog
                System.out.println("运行类型" + o.getClass());
                System.out.println("dog信息" + o);

                // 补充一个特别重要的细节
                // 如果我们希望调用Dog的方法，需要向下转型
                // 此时需要将Dog类的定义，拷贝到可以引用的地方
                Dog dog = (Dog) o;
                System.out.println("名字是" + dog.getName());
                System.out.println("年龄是" + dog.getAge());
                System.out.println("国家是" + Dog.getNation());// null
                System.out.println("颜色是" + dog.getColor());// null

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    // 关闭外层流即可
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
