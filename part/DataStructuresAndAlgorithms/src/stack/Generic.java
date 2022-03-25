package stack;

/**
 * @ClassName Generic
 * @Description 泛型方法中的类型参数和泛型类中的类型参数不一样(即使名称一样 ）
 * @Author Zhangyuhan
 * @Date 2020/9/28
 * @Version 1.0
 */
public class Generic<T> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    // 下面的T和上面的T不同
    public <T> T getParam(T t) {
        return t;
    }
}
