package java_stream;

import java_Lambda._0Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @ClassName _4StreamMap
 * @Description Stream中间操作2：映射
 * @Author Zhangyuhan
 * @Date 2021/11/4
 * @Version 1.0
 */
public class _4StreamMap {
    @Test
    public void test1() {
        // map()-接收一个函数作为参数，将元素转换成其他形式或者提取信息，该函数会被应用到每个元素上，并将其映射成一个新的函数。
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        list.stream().map(String::toUpperCase).forEach(System.out::println);

        // 例子：获取员工姓名长度大于3的员工的姓名
        List<_0Employee> data = _0Employee.getData();
        data.stream().map(_0Employee::getName).filter(name -> name.length() > 3).forEach(System.out::println);
    }

    @Test
    public void test2() {
        // flatMap(Function f)接受一个函数作为参数，将流中的每个值都换成另一个流，然后把所有的流连接成一个流
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        // map：结果时Stream嵌套stream。类似于list的add()
        Stream<Stream<Character>> streamStream = list.stream().map(_4StreamMap::fromStringToStream);
        streamStream.forEach(s -> {
            s.forEach(System.out::println);
        });

        // flatMap:结果是一层Stream。类似于addAll()
        Stream<Character> characterStream = list.stream().flatMap(_4StreamMap::fromStringToStream);
        characterStream.forEach(System.out::println);
    }

    // 将字符串中的多个字符构成的集合转换成对应的Stream实例
    public static Stream<Character> fromStringToStream(String str) {
        char[] chars = str.toCharArray();
        List<Character> list = new ArrayList<>();
        for (char c : chars) {
            list.add(c);
        }

        return list.stream();
    }

    @Test
    public void test3() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);

        List list1 = new ArrayList();
        list1.add(4);
        list1.add(5);
        list1.add(6);

        // list1.add(list);// 4个元素
        list1.addAll(list);// 6个元素
        System.out.println(list1);
    }

}
