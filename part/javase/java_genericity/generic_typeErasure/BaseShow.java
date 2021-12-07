package java_genericity.generic_typeErasure;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BaseShow
 * @Description 类型擦除的基本演示
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class BaseShow {
    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();
        List<String> strList = new ArrayList<>();
        System.out.println(intList.getClass().getSimpleName());// 打印Arraylist,泛型被拆除掉了
        System.out.println(strList.getClass().getSimpleName());// 打印ArrayList,泛型被拆除掉了

        System.out.println(intList.getClass() == strList.getClass());// 打印true

        // 结论：在编译阶段使用不同的泛型，但是编译完成之后，泛型擦除掉，实际上都是相同的类形，它们的类型的内存地址都是一样的
    }
}
