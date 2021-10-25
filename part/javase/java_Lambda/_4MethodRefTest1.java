package java_Lambda;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @ClassName _4MethodRefTest1
 * @Description 方法引用的示例
 * 1.方法引用的使用情境：当要传给lambda体的操作，已经有实现的方法了。可以使用方法引用
 * 2.方法引用本质上就是lambda表达式，而lambda表达式是作为函数式接口的实例出现的，所以，方法引用也是函数式接口的实例
 * 3.使用格式 类(或对象)::方法名
 * 4.具体分为如下的三种情况
 * 对象::非静态方法
 * 类::静态方法
 * 类::非静态方法
 * 5.方法引用的使用要求：要求接口中的抽像方法形参列表和返回值类型与方法引用的参列表和返回值类型都相同！(针对于情况1和情况2)
 * @Author Zhangyuhan
 * @Date 2021/10/21
 * @Version 1.0
 */
public class _4MethodRefTest1 {
    /**
     * 情况1：通过对象来引用方法
     */


    @Test
    public void test1() {
        Consumer<String> con1 = str -> System.out.println(str);
        con1.accept("北京");
        System.out.println("***************");
        Consumer<String> con2 = System.out::println;
        con2.accept("上海");
    }

    @Test
    public void test2() {
        _0Employee emp = new _0Employee(1001, "tom", 23, 5600);
        Supplier<String> sup1 = () -> emp.getName();
        System.out.println(sup1.get());

        System.out.println("**********");

        Supplier<String> sup2 = emp::getName;
        System.out.println(sup2.get());
    }
}
