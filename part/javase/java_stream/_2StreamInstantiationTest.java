package java_stream;

import java_Lambda._0Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @ClassName _2StreamInstantiationTest
 * @Description Stream对象的实例化（4种方式）
 * @Author Zhangyuhan
 * @Date 2021/11/3
 * @Version 1.0
 */
public class _2StreamInstantiationTest {
    // 1.通过集合创建
    @Test
    public void test1() {
        List<_0Employee> list = _0Employee.getData();

        // default Stream<E> stream();返回一个顺序流
        Stream<_0Employee> stream = list.stream();

        // default Stream<E> parallelStream();返回一个并行流
        Stream<_0Employee> employeeStream = list.parallelStream();
    }

    // 2.通过数组创建
    @Test
    public void test2() {
        int[] arr = {1, 2, 3};
        // 调用Arrays类的 static <T>Stream<T> stream(T[] arr);返回一个流
        IntStream stream = Arrays.stream(arr);

        _0Employee e1 = new _0Employee(1001, "tom");
        _0Employee e2 = new _0Employee(1002, "mary");
        _0Employee[] employees = {e1, e2};
        Stream<_0Employee> stream1 = Arrays.stream(employees);
    }

    // 3.通过Stream类的of()
    @Test
    public void test3() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);

        List<_0Employee> list = _0Employee.getData();
        Stream<List<_0Employee>> list1 = Stream.of(list);

        _0Employee e1 = new _0Employee(1001, "tom");
        _0Employee e2 = new _0Employee(1002, "mary");
        Stream<_0Employee> e11 = Stream.of(e1, e2);
    }

    // 4.创建无限流(在需要造数据的时候比较有用)
    @Test
    public void test4() {
        // 迭代
        // public static<T> Stream<T> iterate(final T seed,final UnaryOperator<T> f)

        // 遍历前10个偶数(如果不加上limit(10)的话，会停不下来的;借用终止操作来看效果)
        Stream.iterate(0, t -> t + 2).limit(10).forEach(System.out::println);

        System.out.println("******");

        // 生成(传进去一个供给者)
        // public static <T> Stream<T> generate(Supplier<T> s)
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

}
