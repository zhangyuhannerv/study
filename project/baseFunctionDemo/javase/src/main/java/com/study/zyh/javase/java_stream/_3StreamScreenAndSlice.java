package com.study.zyh.javase.java_stream;

import com.study.zyh.javase.java_Lambda._0Employee;
import org.junit.Test;

import java.util.List;

/**
 * @ClassName _3StreamScreenAndSlice
 * @Description Stream中间操作1：筛选与切片
 * @Author Zhangyuhan
 * @Date 2021/11/3
 * @Version 1.0
 */
public class _3StreamScreenAndSlice {
    @Test
    public void test1() {
        List<_0Employee> list = _0Employee.getData();
        System.out.println(list);
        // filter(Predicate p);
        System.out.println("******");
        list.stream().filter(e -> e.getSalary() >= 7000).forEach(System.out::println);// 过滤掉员工工资小于7000的

        // limit();
        System.out.println("******");
        // 注意：每次建议新建stream。不能使用上面已经执行过终止操作的stream。会报错
        list.stream().limit(2).forEach(System.out::println);// 只保留前2个

        // skip();
        System.out.println("******");
        list.stream().skip(2).forEach(System.out::println);// 过滤掉前2个
        // skip()和limit()是互补的
        // 如果skip()的参数大于集合元素的个数，那么会返回一个空流，比如skip(30)那么上面的例子会什么都不打印

        // distinct();通过流所生成的元素的hashCode()和equals()去除重复元素
        System.out.println("******");
        list.stream().distinct().forEach(System.out::println);// 最后两个元素是重复的。去掉了一个重复的
    }
}
