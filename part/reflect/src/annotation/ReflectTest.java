package annotation;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @ClassName ReflectTest
 * @Description 反射的框架类利用注解解析配置文件
 * @Author Zhangyuhan
 * @Date 2020/9/18
 * @Version 1.0
 */

@Pro(className = "annotation.Demo1", methodName = "show")
public class ReflectTest {
    public static void main(String[] args) throws Exception {
        //可以创建任意类的对象，可以执行任意方法
        /**
         * 前提：不能改变该类的任何代码
         */

        // 1.解析注解
        // 1.1获取被注解修饰的类的字节码文件对象
        Class<ReflectTest> cls = ReflectTest.class;
        // 1.2获取上面的注解对象
        Pro an = cls.getAnnotation(Pro.class);// 这行代码就是在内存中生成了一个该注解子类接口的实现对象
        // 相当于
        /*

        public class ProImpl implements pro{
            public String className(){
                return  "annotation.Demo1";
            }

            public String methodName(){

                return "show";
            }

         */

        // 3.调用注解对象中定义的抽象方法来获取返回值
        String className = an.className();
        String methodName = an.methodName();
        System.out.println(className);
        System.out.println(methodName);

        Class demoCls = Class.forName(className);
        Object object = demoCls.newInstance();
        Method method = demoCls.getMethod(methodName);
        method.invoke(object);

        /*
        步骤总结：
        1.获取注解定义的位置的对象，如Class，Field，Method
        2.获取指定的注解
            利用对象的getAnnotation(传的参数是注解的class对象,如Pro.class)
        3.调用注解中的抽象方法获取配置的属性值
         */
    }
}
