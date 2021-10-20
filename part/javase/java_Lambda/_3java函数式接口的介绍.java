package java_Lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @ClassName _3java函数式接口的介绍
 * @Description java内置的四大核心函数式接口
 * 消费型：Consumer<T>  void accept(T t)
 * 供给型：Supplier<T>  T get()
 * 函数型：Function<T,R> R apply(T t)
 * 断定型：Predicate<T> boolean test(T t)
 * @Author Zhangyuhan
 * @Date 2021/10/9
 * @Version 1.0
 */
public class _3java函数式接口的介绍 {
    @Test
    public void test1() {
        // 以前
        happyTime(500, new Consumer<Double>() {
            @Override
            public void accept(Double aDouble) {
                System.out.println("学习太累了，去天上人间，买了瓶矿泉水，价格为" + aDouble);
            }
        });

        System.out.println("*************");

        // 现在
        happyTime(400, money -> System.out.println("花了" + money + "块"));
    }

    public void happyTime(double money, Consumer<Double> con) {
        con.accept(money);
    }


    @Test
    public void test2() {
        List<String> list = Arrays.asList("北京", "天津", "东京", "西京", "普京");
        // 以前
        List<String> filter = filterString(list, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("京");
            }
        });
        System.out.println(filter);
        System.out.println("**************");
        // lambda
        List<String> filterList = filterString(list, s -> s.contains("东"));
        System.out.println(filterList);
    }

    // 根据给定的规则，过滤集合中的字符串，此规则由Predicate的方法决定
    public List<String> filterString(List<String> list, Predicate<String> pre) {
        List<String> filter = new ArrayList<>();
        for (String s : list) {
            if (pre.test(s)) {
                filter.add(s);
            }
        }
        return filter;
    }
}
