package java_genericity.generic_interface;

/**
 * @ClassName Test
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/12/3
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        Apple apple = new Apple();// 这里的apple可以理解为一个普通的类
        String key = apple.getKey();
        System.out.println(key);

        System.out.println("*************");

        Pear<String, Integer, Boolean> pear = new Pear<>("张三", 25, false);
        String t = pear.getT();// 返回String
        Integer e = pear.getE();// 返回Integer
        Boolean k = pear.getK();// 返回布尔
        Integer key1 = pear.getKey();// 返回Integer

        System.out.println(t);
        System.out.println(e);
        System.out.println(k);
        System.out.println(key1);
    }
}
