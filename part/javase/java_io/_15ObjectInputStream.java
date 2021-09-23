package java_io;

import bean.Dog;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @ClassName _15ObjectInputStream
 * @Description TODO
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
