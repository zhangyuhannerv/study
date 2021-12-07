package java_genericity.generic_reflect;

import java.lang.reflect.Constructor;

/**
 * @ClassName TestPerson
 * @Description 泛型与反射
 * 在反射里面同样是支持泛型的
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class TestPerson {
    public static void main(String[] args) throws Exception {
        // 有泛型的反射
        Class<Person> personClass = Person.class;// Class支持泛型
        Constructor<Person> constructor = personClass.getConstructor();// Constructor支持泛型
        Person person = constructor.newInstance();// constructor支持泛型，所以创建对象的时候直接返回Person对象

        // 没有泛型的反射
        Class personClass1 = Person.class;
        Constructor constructor1 = personClass1.getConstructor();
        Object o = constructor1.newInstance();// 没有泛型，创建对象返回Object类型。所以在后续使用时，需要手动加上强转操作

    }
}
