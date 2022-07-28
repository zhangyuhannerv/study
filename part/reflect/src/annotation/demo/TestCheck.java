package annotation.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName TestCheck
 * @Description 简单的测试框架
 * 当主方法执行后会自动执行被检测的所有方法（加了Check注解的方法），判断方法是否有异常，记录到文件中
 * @Author Zhangyuhan
 * @Date 2020/9/21
 * @Version 1.0
 */
public class TestCheck {
    public static void main(String[] args) throws IOException {
        // 1.创建计算器对象
        Calculator calculator = new Calculator();
        // 2.获取字节码文件对象
        Class cls = calculator.getClass();
        // 3.获取所有的方法
        Method[] methods = cls.getMethods();

        int num = 0;// 出现异常的次数
        BufferedWriter bw = new BufferedWriter(new FileWriter("bug.txt"));

        for (Method method : methods) {
            // 4.判断方法上是否有Check注解
            if (method.isAnnotationPresent(Check.class)) {
                // 5.有，执行
                try {
                    method.invoke(calculator);
                } catch (Exception e) {// 6.捕获异常
                    num++;
                    // 7.记录到文件中
                    bw.write(method.getName() + "方法出异常了");
                    bw.newLine();
                    bw.write("异常的名称：" + e.getCause().getClass().getSimpleName());
                    bw.newLine();
                    bw.write("异常的原因：" + e.getCause().getMessage());
                    bw.newLine();
                    bw.write("----------------");
                    bw.newLine();
                }
            }
        }

        bw.write("本次测试一共出现" + num + "次异常");
        bw.flush();
        bw.close();

    }

    /**
     * 小结：
     *      1.以后大多数时候，使用注解，而不是自定义注解
     *      2.注解给谁用？1：编译器（检查注解有没有问题)，2：给解析程序用，如testCheck类。
     *      3.注解不是程序的一部分，可以理解为注解就是一个标签
     */

}
