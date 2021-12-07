package java_genericity.generic_typeErasure;

/**
 * @ClassName ErasureNumber
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/12/7
 * @Version 1.0
 */
public class ErasureNumber<T extends Number> {
    private T key;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }
}
