package reflect;

import domain.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @ClassName ReflectDemo2
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/15
 * @Version 1.0
 */
public class ReflectDemo3 {
    public static void main(String[] args) throws Exception {
        // 0.获取Person的Class对象
        Class<Person> personClass = Person.class;
        /**
         *  1.获取构造方法们
         *  Constructor<?>[] getConstructors() :获取所有public修饰的构造方法
         *  Constructor getConstructor(类<?>... parameterTypes) :获取指定变量类型的public修饰的构造方法
         *  Constructor<?>[] getDeclaredConstructors() :获取所有的构造方法，不考虑修饰符
         *  Constructor<T> getDeclaredConstructor(类<?>... parameterTypes) ：获取指定变量类型的构造方法
         */

        Constructor<Person> constructor = personClass.getConstructor(String.class, int.class);
        System.out.println(constructor);
        System.out.println("-------------");
        //创建对象
        Person person = constructor.newInstance("张三", 23);
        System.out.println(person.toString());
        System.out.println("-------------");

        Constructor<Person> constructor1 = personClass.getConstructor();
        Person person1 = constructor1.newInstance();
        System.out.println(person1.toString());
        // 如果使用空参数的构造方法来构造对象，操作是可以简化的:可以使用Class里面的newInstance方法
        System.out.println(personClass.newInstance());

        // 构造器的暴力反射
        constructor1.setAccessible(true);
    }
}
