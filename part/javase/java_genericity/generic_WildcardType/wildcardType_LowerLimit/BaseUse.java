package java_genericity.generic_WildcardType.wildcardType_LowerLimit;

import java_genericity.generic_WildcardType.wildcardType_UpperLimit.Animal;
import java_genericity.generic_WildcardType.wildcardType_UpperLimit.Cat;
import java_genericity.generic_WildcardType.wildcardType_UpperLimit.MiniCat;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BaseUse
 * @Description 类型通配符的下限的基本使用
 * 类型通配符的下限:<? super 实参类型>
 * 要求该泛型的类型只能是实参类型或者实参类型的父类类型
 * @Author Zhangyuhan
 * @Date 2021/12/6
 * @Version 1.0
 */
public class BaseUse {
    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        List<Cat> cats = new ArrayList<>();
        List<MiniCat> miniCats = new ArrayList<>();

        showAnimal(animals);// 正常
        showAnimal(cats);// 正常
        // showAnimal(miniCats);// 报错
    }

    /**
     * 类型通配符的下限要求集合只能是Cat或者Cat的父类类型
     *
     * @param list
     */
    public static void showAnimal(List<? super Cat> list) {
        // list.add(new Animal());// 报错，可能和初始化的时候指定的父类不一致
        list.add(new Cat());// 正常,不影响初始化的时候指定的父类
        list.add(new MiniCat());// 正常，不影响初始化的时候指定的父类


        // 关于<? extends T> 和<? super T>的总结
        // <? extends T> 不可以添加元素，但可以取出类型为T的元素。 <? super T> 可以添加T或者T的子类，取出的一定是object类。下限通配符不保证元素数据类型的约束要求

        for (Object o : list) {// Cat的所有父类最终都引申为Object
            System.out.println(o);
        }
    }
}
