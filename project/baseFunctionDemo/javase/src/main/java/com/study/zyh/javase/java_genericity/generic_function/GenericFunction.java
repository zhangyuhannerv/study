package java_genericity.generic_function;

import java_genericity.generic_class.GenericClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @ClassName GenericFunction
 * @Description 泛型方法
 * 1.语法
 * 修饰符 <T,E,...> 返回值类型 方法名(形参列表){
 * 方法体
 * }
 * 注意:
 * 1.修饰符与返回值类型之间的<T,E...>等非常重要,可以理解为声明此方法为泛型方法
 * 2.只有声明了<T,E...>等泛型标识的方法才是泛型方法,泛型类中使用了泛型的成员方法并不是泛型方法
 * 3.<T,E...>表示该方法将使用泛型类型T,E,此时才可以在方法中使用泛型类型T,E....
 * 4.与泛型类/泛型接口的的定义一样,此处的T,E可以随便写为任意标识,常见的如T,E,K,V等形式的参数常用于表示泛型
 * 5.泛型方法是独立于泛型接口/泛型类的
 * @Author Zhangyuhan
 * @Date 2021/12/3
 * @Version 1.0
 */

/**
 * 泛型类的泛型标识是E
 *
 * @param <E>
 */
public class GenericFunction<E> {
    private E e;
    private static Random random = new Random();

    public E getE() {
        return e;
    }

    /**
     * 注意:如果类的成员方法使用了类的泛型标识,那么该成员方法不能声明为静态的
     *
     * @param e
     */
    // public static void setE(E e) {// 报错
    public void setE(E e) {
        this.e = e;
    }


    /**
     * 这是一个典型的泛型方法,可以看到声明为静态是不报错的
     *
     * @param list
     * @param <T>
     * @param <E>
     * @param <K>
     */
    public static <T, E, K> void printType(List<E> list) {// 不报错

    }

    /**
     * 典型的静态的泛型方法,而且采用多个泛型类型
     *
     * @param t
     * @param e
     * @param k
     * @param <T>
     * @param <E>
     * @param <K>
     */
    public static <T, E, K> void printType(T t, E e, K k) {
        System.out.println(t + "\t" + t.getClass().getSimpleName());
        System.out.println(e + "\t" + e.getClass().getSimpleName());
        System.out.println(k + "\t" + k.getClass().getSimpleName());
    }


    /**
     * 定义一个泛型可变参数的静态方法
     *
     * @param e
     * @param <E>
     */
    public static <E> void printType(E... e) {
        for (E e1 : e) {
            System.out.println(e1);
        }
    }

    /**
     * getProduct:这才是泛型方法,注意修饰符public于返回值类型E中间的泛型标识<E>
     *
     * @param list:参数
     * @param <E>:方法的泛型标识,具体类型由调用方法的时候来指定,虽然和泛型类的泛型标识一样都是E,但是他俩毫无关系。 下面方法中所有的E都是由调用方法的时候指定的,和泛型类的E没有任何关系
     * @return
     * @author Zhangyuhan
     * @date 2021/12/3 17:24
     */
    public <E> E getProduct(List<E> list) {
        return list.get(random.nextInt(list.size()));
        // return this.e;// 报错,说明方法的E和类的E毫无关系
    }

    @Test
    public void test() {
        // 这里定义类的时候指定Integer
        GenericFunction<Integer> genericFunction = new GenericFunction<>();
        genericFunction.setE(25);
        // 这里是调用泛型类的成员方法,它不是泛型方法,它必须遵循类的泛型标识
        Integer e = genericFunction.getE();// 返回Integer
        System.out.println(e + "\t" + e.getClass().getSimpleName());
        System.out.println("************");


        String[] strArr = {"笔记本电脑", "苹果手机", "扫地机器人"};
        List<String> strList = Arrays.asList(strArr);


        // 泛型方法的调用,调用的时候指定方法的泛型标识是String
        // 直接返回String,因为strList的泛型标识是String,所以调用方法的时候,String就传给了方法中的E,相当于指定了方法的泛型标识
        // 可以看出泛型方法的泛型标识是调用方法的时候来指定的,可以是任意的泛型标识,可以和类的泛型标识(如果有)不同
        String product = genericFunction.getProduct(strList);
        System.out.println(product + "\t" + product.getClass().getSimpleName());

        System.out.println("************");

        List<Integer> intList = Arrays.asList(1000, 5000, 3000);
        Integer product2 = genericFunction.getProduct(intList);// 返回Integer
        System.out.println(product2 + "\t" + product2.getClass().getSimpleName());
    }

    @Test
    public void test1() {
        // 调用多个泛型类型的静态方法
        GenericFunction.printType(100, "java", "true");
        System.out.println("******");
        GenericFunction.printType(false, true, true);
        System.out.println("******");

        // 可变参数的泛型方法的调用
        GenericFunction.printType(100, "你好啊", true, "哈哈", 'a');// 此时应该是指定的泛型类型为Object
        System.out.println("******");
        GenericFunction.printType(1, 2, 3, 4, 5);// 此时应该是指定的泛型类型为Integer
    }
}
