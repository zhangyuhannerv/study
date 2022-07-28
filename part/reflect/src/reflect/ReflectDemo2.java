package reflect;

import domain.Person;

import java.lang.reflect.Field;

/**
 * @ClassName ReflectDemo2
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/15
 * @Version 1.0
 */
public class ReflectDemo2 {
    public static void main(String[] args) throws Exception {
        // 0.获取Person的Class对象
        Class<Person> personClass = Person.class;
        /**
         *  1.获取成员变量们
         *  Fields[] getFields() :获取所有public修饰的成员变量
         *  Field getField(String name):获取指定名称的public修饰的成员变量
         *  Field[] getDeclaredField() :获取所有的成员变脸，不考虑修饰符
         *  Field getDeclaredField(String name)：获取指定名称的成员变量
         */
        Field[] fields = personClass.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        System.out.println("----------");
        Field a = personClass.getField("a");
        // 获取成员变量a的值
        Person p = new Person();
        Object value = a.get(p);
        System.out.println(value);
        // 设置成员变量a的值
        a.set(p,"张三");
        System.out.println(p);
        System.out.println("-------------");
        Field[] declaredFields = personClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }
        System.out.println("----------");
        Field d = personClass.getDeclaredField("d");
        // 通过类对象访问私有的成员变量时，需要忽略权限修饰符的安全检查
        d.setAccessible(true);// 本行代码又称为暴力反射
        Object value2 = d.get(p);
        System.out.println(value);
    }
}
