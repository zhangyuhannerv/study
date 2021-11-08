package java_stream;

import java_Lambda._0Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName _8StreamCollect
 * @Description Stream的终止操作：收集（Collect）
 * @Author Zhangyuhan
 * @Date 2021/11/8
 * @Version 1.0
 */
public class _8StreamCollect {
    @Test
    public void test() {
        // collect(Collector c) 将流转换成其他形式。接收一个Collector接口的实现，用于给Stream中的元素做汇总
        // Collector接口中的方法实现决定了如何对流执行收集的操作（如收集到List,Set,Map)
        // Collectors提供了很多静态的方法，可以方便的创建收集器实例

        // 练习1：查找工资大于6000的员工，结果返回一个list或者set
        List<_0Employee> data = _0Employee.getData();

        // 把流中元素收集到List
        List<_0Employee> dataList = data.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toList());
        dataList.forEach(System.out::println);// 打印看是否所有的工资都大于6000

        System.out.println("******");
        // 把流中元素收集到Set
        Set<_0Employee> dataList1 = data.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toSet());
        dataList1.forEach(System.out::println);
    }

    @Test
    public void test1() {
        List<_0Employee> data = _0Employee.getData();
        // 把流中元素收集到新创建的集合

        // 收集到新创建的ArrayList
        ArrayList<_0Employee> collect = data.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toCollection(ArrayList::new));
        collect.forEach(System.out::println);

        System.out.println("******");
        // 收集到新创建的HashSet
        HashSet<_0Employee> collect1 = data.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toCollection(HashSet::new));
        collect1.forEach(System.out::println);
    }
}
