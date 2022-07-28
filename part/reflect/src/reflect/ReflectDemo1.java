package reflect;

import domain.Person;
import domain.Student;

/**
 * @ClassName ReflectDemo1
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/15
 * @Version 1.0
 */
public class ReflectDemo1 {
    /**
     * 获取Class对象的三种方式：
     * Class.forName("全限定的类名")：多用于配置文件，将类名定义在配置文件中，读取文件，加载类
     * 类名.class(),通过类名的属性class获取：多用于参数的传递
     * 对象.getClass():getClass()在Object中定义着：对用于对象获取字节码的方式
     */
    public static void main(String[] args) throws Exception {
        // Class.forName("全限定的类名")
        Class<?> aClass = Class.forName("domain.Person");
        System.out.println(aClass);
        // 类名.class
        Class<Person> aClass1 = Person.class;
        System.out.println(aClass1);
        // 对象.getClass()
        Person person = new Person();
        Class aClass2 = person.getClass();
        System.out.println(aClass2);

        System.out.println(aClass == aClass1);// true
        System.out.println(aClass == aClass2);// true

        // 同一个字节码文件(*.class)再一次程序运行过程中，只会被加载一次
        // 不论通过哪一种方式获得的class对象都是同一个

        Class c = Student.class;
        System.out.println(c == aClass);
    }

}
