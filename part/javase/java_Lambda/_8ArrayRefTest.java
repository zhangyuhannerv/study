package java_Lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @ClassName _8ArrayRefTest
 * @Description 数组引用
 * 数组引用：可以把数组看成是一个特殊的类，则写法就与构造器引用一致
 * @Author Zhangyuhan
 * @Date 2021/10/26
 * @Version 1.0
 */
public class _8ArrayRefTest {
    @Test
    // Function R apply(T t)
    public void test4() {
        Function<Integer, String[]> fun1 = (length) -> new String[length];
        String[] apply = fun1.apply(5);
        System.out.println(Arrays.toString(apply));

        System.out.println("****************");

        Function<Integer, String[]> fun2 = String[]::new;
        String[] apply1 = fun2.apply(10);
        System.out.println(Arrays.toString(apply1));
    }
}
