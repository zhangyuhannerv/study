package com.study.zyh.javase.java_stream;

/**
 * @ClassName _1StreamApiTest
 * @Description 主要是介绍Stream一些的基础知识(本类不会有任何代码)
 * 1.Stream关注的是对数据的运算，与cpu打交道
 * 集合关注的是数据的存储，与内存打交道
 * <p>
 * 2.注意：
 * Stream自己不会存储数据
 * Stream不会改变源对象，相反，他们会返回一个持有结果的新Strem
 * Stream的操作是延迟执行的，这意味着，他们会等到需要结果的时候才执行
 * <p>
 * 3.执行流程
 * Stream实例化
 * 一系列的中间操作（过滤，映射)：一个中间操作链，对数据源的数据进行处理
 * 终止操作：执行终止操作的时候，才会去执行中间操作链，并产生结果，之后，该Stream不能再被使用，如需新的Stream，重新创建
 * @Author Zhangyuhan
 * @Date 2021/11/3
 * @Version 1.0
 */
public class _1StreamApiTest {
}
