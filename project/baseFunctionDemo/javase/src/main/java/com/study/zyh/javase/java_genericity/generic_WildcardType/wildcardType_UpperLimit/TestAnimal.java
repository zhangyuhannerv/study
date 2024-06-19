package com.study.zyh.javase.java_genericity.generic_WildcardType.wildcardType_UpperLimit;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TestAnimal
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/12/6
 * @Version 1.0
 */
public class TestAnimal {
    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        List<Cat> cats = new ArrayList<>();
        List<MiniCat> miniCats = new ArrayList<>();

        // cats.add(new Animal());// 报错
        cats.add(new Cat());// 不报错
        cats.add(new MiniCat());// 不报错
        cats.addAll(miniCats);// 不报错


        // showAnimal(animals);// 报错
        showAnimal(cats);// 正常
        showAnimal(miniCats);// 正常
    }

    /**
     * 泛型通配符的上限，传递的集合类型只能是Cat或Cat的子类类型
     *
     * @param list
     */
    public static void showAnimal(List<? extends Cat> list) {
        // 注意，在此例中使用类型通配符上限的形参集合是不能向里面添加元素的
        // list.add(new Animal());// 报错，超过通配符上限
        // list.add(new Cat());// 报错，List里面本质是<?>,不能确定为到底是哪一个类型
        // list.add(new MiniCat());// 报错,List里面本质是<?>,不能确定为到底是哪一个类型。比如List里面都是Cat，添加一个MiniCat。就是错的

        for (Cat cat : list) {
            System.out.println(cat);
        }
    }
}
