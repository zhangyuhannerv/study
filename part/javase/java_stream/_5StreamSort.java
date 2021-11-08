package java_stream;

import java_Lambda._0Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName _5StreamSort
 * @Description Stream的中间操作:排序
 * @Author Zhangyuhan
 * @Date 2021/11/8
 * @Version 1.0
 */
public class _5StreamSort {
    @Test
    public void test() {
        // sorted()自然排序
        List<Integer> ints = Arrays.asList(1, 2, 34, 4, 52, 7);
        ints.stream().sorted().forEach(System.out::println);
    }

    @Test
    public void test1() {
        // sorted(Comparator com) 定制排序
        List<_0Employee> data = _0Employee.getData();
        // data.stream().sorted().forEach(System.out::println);// 直接排序会报错（因为_0Employee类没有实现Comparable接口)

        // 根据工资排序
        // data.stream().sorted(Comparator.comparingDouble(_0Employee::getSalary)).forEach(System.out::println);

        // 先根据年龄排序，年龄相等再根据工资排序
        data.stream().sorted((e1, e2) -> {
            int ageValue = Integer.compare(e1.getAge(), e2.getAge());
            if (ageValue != 0) {
                return ageValue;
            } else {
                return Double.compare(e1.getSalary(), e2.getSalary());
            }
        }).forEach(System.out::println);
    }
}
