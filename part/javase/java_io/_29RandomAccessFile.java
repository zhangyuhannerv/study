package java_io;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName _29RandomAccessFile
 * @Description RandomAccessFile:任意读写文件流：既可以作为输入流，又可以作为输出流
 * 该类支持‘随机访问‘的方式，程序可以跳到文件的任意位置来读写文件
 * >支持只访问文件的部分内容
 * >可以向以存在的文件后面追加内容
 * 1.该类直接继承Object，而不是继承IO流的四大基类
 * 2.实现了DataInput和DataOutPut
 * 3.一个实例既可以作为输入流，又可以作为输出流
 * 4.如果RandomAccessFile作为输出流，写出到的文件如果不存在，则在执行过程中，自动创建。
 * 如果写出到的文件存在，则会对文件原有内容进行覆盖（默认情况下，从头覆盖）
 * 5.可以通过相关的操作，实现RandomAccessFile"插入"数据的效果
 * 6.该类的比较重要的应用案例：大文件多线程断点下载/上传
 * @Author Zhangyuhan
 * @Date 2021/11/12
 * @Version 1.0
 */
public class _29RandomAccessFile {
    // 测试RandomFileAccess简单的读写
    @Test
    public void test1() {
        // mode:
        // r:以只读的方式打开，文件不存在会报错
        // rw:打开随便读取和写入，文件不存在会创建文件，在程序中写的内容不会立即同步到文件中。写的过程中报错，所写的数据全部消失
        // rwd:打开随便读取和写入；同步文件内容的更新,在程序中写入的内容会立即同步到文件中。写的过程中报错。已经写上的保留，报错及之后的部分消失
        // rws:打开随便读取和写入；同步文件内容和元数据的更新
        try (RandomAccessFile raf1 = new RandomAccessFile(new File("D:\\javaTest\\1.txt"), "r");
             RandomAccessFile raf2 = new RandomAccessFile(new File("D:\\javaTest\\randomTest.txt"), "rw");) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = raf1.read(bytes)) != -1) {
                raf2.write(bytes, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 测试RandomAccessFile对文件的覆盖方式
    // 从文件的开头，新写的内容有多少就写上多少。原有的相同长度被盖掉，如果新写的内容不够文件的原长度。那么文件还会剩下一部分旧有的内容
    @Test
    public void test2() {
        try (RandomAccessFile raf1 = new RandomAccessFile(new File("D:\\javaTest\\randomTest.txt"), "rw")) {
            raf1.write("世人笑我太疯癫".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 需求：在文件的中间覆盖内容
    @Test
    public void test3() {
        try (RandomAccessFile raf1 = new RandomAccessFile(new File("D:\\javaTest\\randomTest.txt"), "rw")) {
            // 指针初始位置在0(初始位置移动的字节数为0)
            // 把指针向后移动两个字节然后写入abc覆盖掉原先与abc长度相同的内容
            raf1.seek(2);// 这里如果传入file.length(),那么就是在最后追加内容
            raf1.write("abc".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 需求：实现插入(通过StringBuilder来存取后面的内容)
    // 需求：在文件的中间插入内容
    @Test
    public void test4() {
        File file = new File("D:\\javaTest\\randomTest.txt");
        try (RandomAccessFile raf1 = new RandomAccessFile(file, "rw")) {
            // 把指针向后移动两个字节
            raf1.seek(2);
            // 保存两个字节之后的所有数据到StringBuilder里面
            StringBuilder builder = new StringBuilder((int) file.length());// 为了防止StringBuilder(默认是16字节的长度)底层扩容
            byte[] bytes = new byte[20];
            int len;
            while ((len = raf1.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, len));
            }
            // 都读完之后，指针跑到最后，重新掉回到需要插入的位置
            raf1.seek(2);
            // 写入需要插入的内容
            raf1.write("xyz".getBytes());
            // 写入原先的内容
            raf1.write(builder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 思考：将StringBuilder替换为ByteArrayOutputStream
    }

    // 实现插入：通过(ByteArrayOutputStream存储)
    // 解决思考题
    @Test
    public void test5() {
        File file = new File("D:\\javaTest\\randomTest.txt");
        try (RandomAccessFile raf1 = new RandomAccessFile(file, "rw");
             ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();) {
            // 把指针向后移动两个字节
            raf1.seek(2);

            byte[] bytes = new byte[20];
            int len;
            while ((len = raf1.read(bytes)) != -1) {
                byteBuffer.write(bytes, 0, len);
            }
            // 都读完之后，指针跑到最后，重新掉回到需要插入的位置
            raf1.seek(2);
            // 写入需要插入的内容
            raf1.write("ByteArrayOutputStream".getBytes());
            // 写入原先的内容
            raf1.write(byteBuffer.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
