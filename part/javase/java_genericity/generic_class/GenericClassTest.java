package java_genericity.generic_class;

/**
 * @ClassName GenericClassTest
 * @Description 测试泛型类
 * @Author Zhangyuhan
 * @Date 2021/12/3
 * @Version 1.0
 */
public class GenericClassTest {
    public static void main(String[] args) {
        // 泛型类在创建对象的时候，来指定操作的具体数据类型
        GenericClass<String> obj1 = new GenericClass<>();
        String t1 = obj1.getT(); // 直接返回String
        System.out.println(t1);
        System.out.println(obj1);
        System.out.println(obj1.getClass());

        System.out.println("**********");

        GenericClass<String> obj2 = new GenericClass<>("123");
        String t2 = obj2.getT(); // 直接返回String
        System.out.println(t2);
        System.out.println(obj2);
        System.out.println(obj2.getClass());

        System.out.println("**********");

        GenericClass<Integer> obj3 = new GenericClass<>(456);
        Integer t3 = obj3.getT(); // 直接返回Integer
        System.out.println(t3);
        System.out.println(obj3);
        System.out.println(obj3.getClass());

        System.out.println("**********");

        GenericClass obj4 = new GenericClass("ABC");// 不传递参数类型。那么会默认会是Object类型
        Object t4 = obj4.getT(); // 直接返回Object
        System.out.println(t4);
        System.out.println(obj4);
        System.out.println(obj4.getClass());

        System.out.println("**********");
        System.out.println("class对象是否相同呢？");
        System.out.println(obj1.getClass() == obj2.getClass());
        System.out.println(obj2.getClass() == obj3.getClass());
        System.out.println(obj3.getClass() == obj4.getClass());


        // 结论：只声明了一个类，就能操作不同的数据类型
        // 注意：
        // 1.泛型类在创建对象的时候，如果没有指定类型，那么将按照Object类型来操作
        // 2.泛型类不支持基本数据类型（8大基本数据类型）,因为泛型实际上还是操作object，只是在适当的时机转换成我们传递过去的类型参数。
        // 而基本类型不是继承于object类的
        // 3.同一泛型类，根据不同的数据类型创建的对象本质上是同一个类型（比如在本demo中都是GenericClass类型)
    }
}
