package java_io;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName _33CommonIOJarTest
 * @Description 测试apache开源的jar包commons-io(第三方封装好的关于io的一些api) 来读写数据
 * @Author Zhangyuhan
 * @Date 2021/11/12
 * @Version 1.0
 */
public class _33CommonIOJarTest {
    @Test
    public void test() {
        File file0 = new File("D:\\javaTest\\source.jpg");
        File file1 = new File("D:\\javaTest\\source1.jpg");
        try {
            FileUtils.copyFile(file0, file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
