package java_Lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Function;

/**
 * @ClassName _5MethodRefTest2
 * @Description 方法引用类型二：类：静态方法
 * @Author Zhangyuhan
 * @Date 2021/10/25
 * @Version 1.0
 */
public class _5MethodRefTest2 {
    public static void main(String[] args) {
        Comparator<Integer> com1 = (t1, t2) -> Integer.compare(t1, t2);
        System.out.println(com1.compare(1, 2));
        System.out.println("****************");
        Comparator<Integer> com2 = Integer::compare;
        System.out.println(com2.compare(12, 6));
    }

    @Test
    public void test() {
        // 之前
        Function<Double, Long> f0 = new Function<Double, Long>() {
            @Override
            public Long apply(Double d) {
                return Math.round(d);
            }
        };

        // 现在
        Function<Double, Long> f1 = d -> Math.round(d);
        System.out.println(f1.apply(12.3));
        System.out.println("*****************");
        // 现在在
        Function<Double, Long> f2 = Math::round;
        System.out.println(f2.apply(12.6));
    }
}
