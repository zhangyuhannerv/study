package reflect;

import domain.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @ClassName ReflectDemo2
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/15
 * @Version 1.0
 */
public class ReflectDemo4 {
    public static void main(String[] args) throws Exception {
        // 0.获取Person的Class对象
        Class<Person> personClass = Person.class;
        /**
         *  1.获取成员方法们
         *  getDeclaredMethod(String name, 类<?>... parameterTypes)
         * 返回一个 方法对象，它反映此表示的类或接口的指定声明的方法 类对象。
         * getDeclaredMethods()
         * 返回包含一个数组 方法对象反射的类或接口的所有声明的方法，通过此表示 类对象，包括公共，保护，默认（包）访问和私有方法，但不包括继承的方法。
         * getMethod(String name, 类<?>... parameterTypes)
         * 返回一个 方法对象，它反映此表示的类或接口的指定公共成员方法 类对象。
         * getMethods()
         * 返回包含一个数组 方法对象反射由此表示的类或接口的所有公共方法 类对象，包括那些由类或接口和那些从超类和超接口继承的声明。
         */

        Method eat_method = personClass.getMethod("eat");
        //执行方法：invoke(对象，方法所需的参数列表）
        eat_method.invoke(new Person());

        Method eat_method2 = personClass.getMethod("eat", String.class);
        eat_method2.invoke(new Person(),"三明治");
        System.out.println("-----");
        // 获取所有public修饰的方法
        Method[] methods = personClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);

            // getName()是获取方法的名称
            System.out.println(method.getName());
        }

        // method的暴力反射
        eat_method.setAccessible(true);

        System.out.println("-------------------");
        // Class对象获取类名，是全类名
        System.out.println(personClass.getName());// domain.Person
    }

}
