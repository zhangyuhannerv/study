package java_stream;

import java_Lambda._0Employee;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName _6StreamMatchAndSearch
 * @Description Stream的终止操作：匹配与查找(680课)
 * @Author Zhangyuhan
 * @Date 2021/11/8
 * @Version 1.0
 */
public class _6StreamMatchAndSearch {
    // 匹配
    @Test
    public void test() {
        List<_0Employee> data = _0Employee.getData();
        // allMatch(Predicate p)检查是否匹配所有元素
        // 例：是否所有的员工的年龄都大于18
        boolean allMatch = data.stream().allMatch(e -> e.getAge() > 18);
        System.out.println(allMatch);

        // anyMatch(Predicate p)检查是否匹配至少一个元素
        // 例子：是否存在员工的工资大于10000
        System.out.println(data.stream().anyMatch(e -> e.getSalary() > 10000));

        // noneMatch(Predicate p)检查是否所有元素都不匹配
        // 例子：是否存在员工姓雷(只要有一个姓雷的，就是false，反之，所有员工的名字都不包含雷，就是true)
        System.out.println(data.stream().noneMatch(e -> e.getName().contains("雷")));
    }

    // 查找
    @Test
    public void test1() {
        List<_0Employee> data = _0Employee.getData();

        // findFirst()// 返回第一个元素
        Optional<_0Employee> first = data.stream().findFirst();
        System.out.println(first);


        // findAny()// 返回任意元素
        Optional<_0Employee> any = data.stream().findAny();// 串行流永远返回第一个
        System.out.println(any);

        Optional<_0Employee> any1 = data.parallelStream().findAny();// 并行流返回不确定的一个(和cpu有关)
        System.out.println(any1);

        // count()求个数(返回long)
        long count = data.stream().filter(e -> e.getSalary() > 7000).count();
        System.out.println(count);

        // max(Comparator m)返回流中的最大值，是个Optional<T>对象
        // 例子：返回最高的工资的员工
        System.out.println(data.stream().max(Comparator.comparingDouble(_0Employee::getSalary)));

        // min(Comparator m)返回流中的最小值，是个Optional<T>对象
        // 例子：返回最小工资的员工。同上，具体的就不写了

        // forEach() 内部迭代
        System.out.println("----forEach()----");
        data.stream().limit(1).forEach(System.out::println);

        // 这是集合的本身的forEach()方法，是外部迭代
        data.forEach(System.out::println);
    }

}
