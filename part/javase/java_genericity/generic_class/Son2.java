package java_genericity.generic_class;
/**
 * @ClassName Son1
 * @Description 泛型类派生子类2
 * @Author Zhangyuhan
 * @Date 2021/12/3
 * @Version 1.0
 */

import org.junit.Test;

/**
 * 泛型类派生子类如果不是泛型类，那么父类要明确泛型标识（数据类型)是什么
 */

// 如果父类不指定泛型标识，那么就是Object
// public class Son2 extends Parent {
// @Override
// public Object getValue() {
//     return super.getValue();
// }

// 父类没有指定泛型标识报错
// public class Son2 extends Parent<E> {// 报错

// 父类制定了泛型标识为Integer，正常
public class Son2 extends Parent<Integer> {
    @Override
    public Integer getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(Integer value) {
        super.setValue(value);
    }

    @Test
    public void test() {
        Son2 son2 = new Son2();// 可以理解为就是一个普通的类
        son2.setValue(100);
        System.out.println(son2.getValue());
    }
}