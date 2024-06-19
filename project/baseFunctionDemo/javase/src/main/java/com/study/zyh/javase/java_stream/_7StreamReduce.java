package com.study.zyh.javase.java_stream;

import com.study.zyh.javase.java_Lambda._0Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName _7StreamReduce
 * @Description Stream的终止操作：规约(reduce)
 * @Author Zhangyuhan
 * @Date 2021/11/8
 * @Version 1.0
 */
public class _7StreamReduce {
    @Test
    public void test() {
        // reduce(T identity,BinaryOperator)，可以将流中的元素反复结合起来，得到一个值，返回T
        // 例子1：计算1-10的自然数的和
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // 0相当于初始值，BinaryOperator传进去两个同一类型的值，返回一个同一类型的值
        Integer reduce = list.stream().reduce(0, Integer::sum);
        System.out.println(reduce);

        // 例子2：计算公司所有员工的工资的总和
        List<_0Employee> data = _0Employee.getData();
        Optional<Double> reduce1 = data.stream().map(_0Employee::getSalary).reduce(Double::sum);
        System.out.println(reduce1);
    }
}
