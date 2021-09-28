package java_io;

import java.io.*;

/**
 * @ClassName _24ReadPropertiesFile
 * @Description 传统的方式读取配置文件（mysql.properties)，并得到相应的ip,user和pwd
 * @Author Zhangyuhan
 * @Date 2021/9/28
 * @Version 1.0
 */
public class _24ReadPropertiesFile {
    public static void main(String[] args) {
        BufferedReader bufferedReader = null;
        // 这里读取的是项目的相对路径
        // 默认父目录是根目录
        String filePath = "java_io\\mysql.properties";
        String line = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("=");
                System.out.println(split[0] + "值:" + split[1]);

                // 如果需求是指定得到用户名
                // 那么需要自己通过循环判断哪个是我们需要获得的属性(传统的很垃圾)
                if ("user".equals(split[0])) {
                    System.out.println("用户名是" + split[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
