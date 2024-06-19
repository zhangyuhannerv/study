package java_genericity.generic_class;

import org.junit.Test;
/**
 * @ClassName Son1
 * @Description 泛型类派生子类1
 * @Author Zhangyuhan
 * @Date 2021/12/3
 * @Version 1.0
 */

/**
 * 1.如果子类也是泛型类，那么子类和父类的泛型标识要一致
 * 这是因为创建子类对象时要先创建父类对象，java要把子类的泛型标识传递给父类，如果父子泛型标识不一致，父类无法得知泛型标识是什么，就会导致父类创建不成功
 * 2.但是我们可以在子类上做一些泛型扩展，如下面的E,K。但是无论如何T,E,K里面要有一个泛型标识和父类的保持一致（哪个都行，不一定是第一个)
 */

// public class Son1<T> extends Parent<E> {// 报错
public class Son1<T, E, K> extends Parent<T> {
    // 在子类上做一些泛型扩展
    private E e;
    private K k;

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    @Test
    public void test() {
        Son1 son1 = new Son1();
        son1.setValue("23");
        Object value1 = son1.getValue();// 返回Object
        System.out.println(value1);

        System.out.println("***************");

        Son1<String, Integer, Double> son2 = new Son1<>();
        son2.setValue("45");
        String value2 = son2.getValue();// 返回String
        System.out.println(value2);
    }
}

