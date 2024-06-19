package java_genericity.generic_typeErasure;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName FunctionTypeErasure
 * @Description 演示方法的类型擦除
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class FunctionTypeErasure {
    // 这是一个泛型方法
    public static <T extends List> T show(T t) {
        return t;
    }

    // 这是一个泛型方法
    public static <T> T show1(T t) {
        return t;
    }

    public static void main(String[] args) {
        Method[] methods = FunctionTypeErasure.class.getDeclaredMethods();// 反射获取类的所有方法
        for (Method method : methods) {
            System.out.println(method.getName() + ":" + method.getReturnType().getSimpleName());// 打印方法名和返回值类型的短名
        }
        // show()方法返回值是List，这是一种有限制的类型擦除
        // show1()方法返回值是Object，这是一种无限制的类型擦除
        // 注意泛型下限也是一种无限制的类型擦除，统一转成Object
    }
}
