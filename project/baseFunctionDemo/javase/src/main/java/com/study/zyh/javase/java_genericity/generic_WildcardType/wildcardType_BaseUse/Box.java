package java_genericity.generic_WildcardType.wildcardType_BaseUse;

/**
 * @ClassName Box
 * @Description 泛型类Box。
 * @Author Zhangyuhan
 * @Date 2021/12/6
 * @Version 1.0
 */
public class Box<E> {
    private E e;

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }
}
