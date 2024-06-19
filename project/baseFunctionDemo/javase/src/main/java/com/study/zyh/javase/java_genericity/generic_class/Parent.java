package java_genericity.generic_class;

/**
 * @ClassName Parent
 * @Description 泛型父类
 * @Author Zhangyuhan
 * @Date 2021/12/3
 * @Version 1.0
 */
public class Parent<E> {
    private E value;

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
