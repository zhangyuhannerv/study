package java_genericity.generic_WildcardType.wildcardType_UpperLimit;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * @ClassName TestTreeSet
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class TestTreeSet {
    public static void main(String[] args) {
        TreeSet<Cat> treeSet1 = new TreeSet<>(Comparator.comparing(Animal::getName));// 根据Animal的name属性去比较
        treeSet1.add(new Cat("jerry", 20));
        treeSet1.add(new Cat("amy", 22));
        treeSet1.add(new Cat("frank", 35));
        treeSet1.add(new Cat("jim", 15));
        for (Cat cat : treeSet1) {
            System.out.println(cat);
        }

        System.out.println("***********");

        TreeSet<Cat> treeSet2 = new TreeSet<>(Comparator.comparing(Cat::getAge));// 根据Cat的age属性去比较
        treeSet2.add(new Cat("jerry", 20));
        treeSet2.add(new Cat("amy", 22));
        treeSet2.add(new Cat("frank", 35));
        treeSet2.add(new Cat("jim", 15));
        for (Cat cat : treeSet2) {
            System.out.println(cat);
        }

        // TreeSet<Cat> treeSet3 = new TreeSet<>(Comparator.comparing(MiniCat::getLevel));// 根据MiniCat的level属性去比较,此时报错。因为构造器是下限通配符
    }
}
