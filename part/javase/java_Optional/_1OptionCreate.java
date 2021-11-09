package java_Optional;

import org.junit.Test;

import java.util.Optional;

/**
 * @ClassName _1OptionCreate
 * @Description Optional的常见的创建实例的方法
 * @Author Zhangyuhan
 * @Date 2021/11/9
 * @Version 1.0
 */
public class _1OptionCreate {

    // Optional.of(T t)：创建一个Optional实例，t必须为非空
    @Test
    public void test() {
        Girl girl = new Girl();
        // girl = null;// 如果girl是null，那么下面创建Optional对象就报错了
        // of(T t)必须保证t为非空的
        Optional<Girl> optionalGirl = Optional.of(girl);
    }

    // Optional.empty():创建一个空的Optional实例
    // 不具体写代码了

    // Optional.ofNullable(T t):创建一个Optional实例，T可以为空
    @Test
    public void test1() {
        Girl girl = new Girl();
        girl = null;// 如果girl是null，下面创建Optional对象不会报错
        // ofNullable(T t)的t可以为null
        Optional<Girl> optionalGirl = Optional.ofNullable(girl);
        System.out.println(optionalGirl);// 打印Option.empty
    }
}
